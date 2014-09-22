package nonblocking;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Does not work. :-P
public class NonBlockingClientServer {

  private static final int BUFFER_SIZE = 1024;

  public static class NonBlockingServer implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(NonBlockingServer.class);
    private final ServerSocketChannel srv;

    public NonBlockingServer(int port) throws IOException {
      this.srv = ServerSocketChannel.open();
      this.srv.configureBlocking(false);
      this.srv.bind(new InetSocketAddress(port));
      log.info("Server message listener started on port {}!", port);
    }

    public void registerSelector(Selector selector) throws ClosedChannelException {
      this.srv.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void close() throws IOException {
      if (srv != null) {
        srv.close();
      }
    }

    public static void handleAccept(SelectionKey key) throws IOException {
      ServerSocketChannel srv = (ServerSocketChannel) key.channel();
      SocketChannel client = srv.accept();
      client.configureBlocking(false);
      ServerState state = new ServerState();
      client.register(key.selector(), SelectionKey.OP_READ, state);
      log.debug("{}: accepted socket", state.workerName);
    }

    public static boolean handleRead(SelectionKey key, ServerState state) throws IOException {
      NumberMessage msg = readFromChannel(key);
      if (msg == null) {
        log.debug("{}: trying to read from a channel but there are too few bytes", state.workerName);
        return false;
      } else {
        log.debug("{}: read message {}", state.workerName, msg);
        NumberMessage response = doBusiness(msg);
        state.msg = response;
        key.interestOps(SelectionKey.OP_WRITE);
        return true;
      }
    }

    public static boolean handleWrite(SelectionKey key, ServerState state) throws IOException {
      NumberMessage msg = state.msg;
      if (msg == null) {
        log.debug("{}: trying to write to a channel while no message is ready yet", state.workerName);
        return false;
      } else {
        writeToChannel(key, msg);
        log.debug("{}: wrote message {}", state.workerName, msg);
        key.interestOps(SelectionKey.OP_READ);
        return true;
      }
    }

    private static NumberMessage doBusiness(NumberMessage msg) {
      return new NumberMessage(msg.number * msg.number);
    }

  }

  public static NumberMessage readFromChannel(SelectionKey key) throws IOException {
    SocketChannel client = (SocketChannel) key.channel();
    ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
    client.read(buf);
    buf.flip();
    if (NumberMessage.hasFullMessage(buf)) {
      return new NumberMessage(buf);
    }
    return null;
  }

  public static void writeToChannel(SelectionKey key, NumberMessage msg) throws IOException {
    if (msg == null) {
      throw new IllegalStateException("tried to write a null message!");
    }
    SocketChannel client = (SocketChannel) key.channel();
    ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
    msg.write(buf);
    buf.flip();
    client.write(buf);
  }

  public static class ServerState {
    private static int workerCounter = 0;

    String workerName = "worker-" + (++workerCounter);
    NumberMessage msg;
  }

  public static class ClientState {
    private static int clientCounter = 0;

    private final long[] numbers;
    private final long sleepBetweenNumbers;

    String clientName = "client-" + (++clientCounter);
    boolean isConnected = false;
    NumberMessage msg;
    int currentNumberIndex = 0;
    long lastMessageReceiveTimestamp = System.currentTimeMillis();

    public ClientState(long sleepBetweenNumbers, long... numbers) {
      this.sleepBetweenNumbers = sleepBetweenNumbers;
      this.numbers = numbers;
    }

    NumberMessage nextMessage() {
      if (System.currentTimeMillis() - lastMessageReceiveTimestamp >= sleepBetweenNumbers) {
        return new NumberMessage(numbers[currentNumberIndex]);
      }
      return null;
    }

    void gotResponse(NumberMessage msg) {
      currentNumberIndex++;
    }

    boolean isDone() {
      return currentNumberIndex < numbers.length;
    }
  }

  public static void main(String[] args) {
    Logger log = LoggerFactory.getLogger("main");
    int port = 8099;
    List<Closeable> closeables = new ArrayList<>();
    try {
      try (NonBlockingServer srv = new NonBlockingServer(port)) {
        Selector selector = Selector.open();
        srv.registerSelector(selector);
        List<ClientState> clientStates = new ArrayList<ClientState>(10);
        NonBlockingClient cl1 = new NonBlockingClient("localhost", port);
        closeables.add(cl1);
        clientStates.add(cl1.registerSelector(selector, new ClientState(1000, 1,2,3,4,5,6,7,8,9,10)));
        NonBlockingClient cl2 = new NonBlockingClient("localhost", port);
        closeables.add(cl2);
        clientStates.add(cl2.registerSelector(selector, new ClientState(1000, 11,12,13,14,15)));
        while (clientStates.size() > 0) {
          int readyChannels = selector.select();
          if(readyChannels == 0) {
            continue;
          }
          Set<SelectionKey> selectedKeys = selector.selectedKeys();
          for (Iterator<SelectionKey> iter = selectedKeys.iterator(); iter.hasNext();) {
            SelectionKey key = iter.next();
            boolean isEventHandled = false;
            if (key.isAcceptable()) {
              NonBlockingServer.handleAccept(key);
              isEventHandled = true;
            } else if (key.isConnectable()) {
              ClientState state = (ClientState) key.attachment();
              NonBlockingClient.handleConnect(key, state);
              isEventHandled = true;
            } else if (key.isReadable()) {
              Object att = key.attachment();
              if (att instanceof ServerState) {
                isEventHandled = NonBlockingServer.handleRead(key, (ServerState) att);
              } else {
                ClientState state = (ClientState) att;
                isEventHandled = NonBlockingClient.handleRead(key, state);
                if (state.isDone()) {
                  clientStates.remove(state);
                }
              }
            } else if (key.isWritable()) {
              Object att = key.attachment();
              if (att instanceof ServerState) {
                isEventHandled = NonBlockingServer.handleWrite(key, (ServerState) att);
              } else {
                isEventHandled = NonBlockingClient.handleWrite(key, (ClientState) att);
              }
            }
            if (isEventHandled) {
              //log.debug("removed key: {}", key);
              //iter.remove();
            }
          }
        }
      }
    }
    catch (Exception e) {
      log.error("", e);
    } finally {
      close(log, closeables);
    }
  }

  private static void close(Logger log, List<Closeable> closeables) {
    for (Closeable closeable : closeables) {
      try {
        closeable.close();
      }
      catch (IOException e) {
        log.debug("error while closing", e);
      }
    }
  }

  public static class NonBlockingClient implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(NonBlockingClient.class);

    private volatile SocketChannel s;

    public NonBlockingClient(String host, int port) throws IOException {
      s = SocketChannel.open();
      s.configureBlocking(false);
      s.connect(new InetSocketAddress(host, port));
    }

    public ClientState registerSelector(Selector selector, ClientState state) throws ClosedChannelException {
      this.s.register(selector, SelectionKey.OP_CONNECT, state);
      return state;
    }

    public static void handleConnect(SelectionKey key, ClientState state) throws IOException {
      SocketChannel socketChannel = (SocketChannel) key.channel();
      socketChannel.finishConnect();
      if (!state.isConnected) {
        state.isConnected = true;
        log.debug("{}: connection established", state.clientName);
        key.interestOps(SelectionKey.OP_WRITE);
        //handleWrite(key, state);
      }
    }

    public static boolean handleWrite(SelectionKey key, ClientState state) throws IOException {
      NumberMessage msg = state.nextMessage();
      if (msg == null) {
        log.debug("{}: trying to write to a channel while no message is ready yet", state.clientName);
        return false;
      } else {
        writeToChannel(key, msg);
        log.debug("{}: wrote message {}", state.clientName, msg);
        key.interestOps(SelectionKey.OP_READ);
        return true;
      }
    }

    public static boolean handleRead(SelectionKey key, ClientState state) throws IOException {
      NumberMessage msg = readFromChannel(key);
      if (msg == null) {
        log.debug("{}: trying to read from a channel but there are too few bytes", state.clientName);
        return false;
      } else {
        log.debug("{}: read message {}", state.clientName, msg);
        state.gotResponse(msg);
        if (!state.isDone()) {
          key.interestOps(SelectionKey.OP_WRITE);
        }
        return true;
      }
    }

    @Override
    public void close() throws IOException {
      if (this.s != null) {
        this.s.close();
      }
    }

  }

  private static class NumberMessage {

    private static final int SIZE = sizeOf();

    private long number;

    public NumberMessage(long number) {
      this.number = number;
    }

    public NumberMessage(ByteBuffer buffer) {
      this.number = buffer.getLong();
    }
    public void write(ByteBuffer buffer) {
      buffer.putLong(number);
      buffer.flip();
      byte[] result = new byte[buffer.remaining()];
      buffer.get(result);
    }

    public static int sizeOf() {
      ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
      new NumberMessage(Long.MAX_VALUE).write(buffer);
      buffer.flip();
      return buffer.remaining();
    }

    @Override
    public String toString() {
      return "NumberMessage [number=" + number + "]";
    }

    private static boolean hasFullMessage(ByteBuffer buf) {
      return buf.remaining() >= SIZE;
    }

  }


}
