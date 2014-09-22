package datagram;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BroadcastClientServer {

  public static class DatagramServer implements Runnable, Closeable {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    private DatagramSocket srv;
    private final Thread serverThread;
    private volatile boolean isClosing = false;

    private final long[] numbers;
    private final long sleepBetweenNumbers;

    private final InetAddress address;
    private final int port;

    private final Runnable callback;

    public DatagramServer(String host, int port, Runnable callback, long sleepBetweenNumbers, long... numbers) throws IOException {
      this.address = InetAddress.getByName(host);
      this.port = port;
      this.callback = callback;
      this.sleepBetweenNumbers = sleepBetweenNumbers;
      this.numbers = numbers;
      this.serverThread = new Thread(this, "sender");
      this.serverThread.setDaemon(false);
      this.serverThread.start();
      log.info("Server message broadcaster started for destination {}:{}!", address, port);
    }

    @Override
    public void run() {
      try {
        //open socket
        log.info("Server opening socket");
        this.srv = new DatagramSocket();
        log.info("Server socket opened on {}:{}", srv.getLocalAddress(), srv.getLocalPort());
        //send and receive messages
        for (long number : numbers) {
          if (sleepBetweenNumbers > 0) {
            log.info("Sleeping for {}ms", sleepBetweenNumbers);
            Thread.sleep(sleepBetweenNumbers);
          }
          NumberMessage request = new NumberMessage(number);
          log.info("Server sending request {}", request);
          this.srv.send(makeDatagramPacket(address, port, request));
        }
        //clean up
        log.info("Server is done");
        if (callback != null) {
          callback.run();
        }
        close();
      }
      catch (IOException e) {
        if (!isClosing) {
          log.error("error in client", e);
        } else {
          log.trace("error in client while closing", e);
        }
      }
      catch (InterruptedException e) {
        if (!isClosing) {
          log.error("client was interrupted while running", e);
        }
      }
    }

    @Override
    public void close() throws IOException {
      this.isClosing = true;
      DatagramSocket s = this.srv;
      this.srv = null;
      if (s != null) {
        log.info("Server disconnecting on {}:{}", s.getLocalAddress(), s.getLocalPort());
        s.close();
      }
    }

  }

  public static void main(String[] args) {
    Logger log = LoggerFactory.getLogger("main");
    int port = 8099;
    List<Closeable> closeables = new ArrayList<>();
    try {
      String host = "228.5.6.7";
      Runnable closeClients = new Runnable() {
        public void run() {
          close(log, closeables);
        }
      };
      try (DatagramServer srv = new DatagramServer(host, port, closeClients, 1000, 1,2,3,4,5,6,7,8,9,10)) {
        DatagramClient cl1 = new DatagramClient(host, port);
        closeables.add(cl1);
        DatagramClient cl2 = new DatagramClient(host, port);
        closeables.add(cl2);
        cl1.join();
        cl2.join();
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

  public static class DatagramClient implements Runnable, Closeable {

    private static int clientCounter = 0;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final Thread clientThread;

    private final MulticastSocket s;

    private volatile boolean isClosing = false;

    public DatagramClient(String host, int port) throws IOException {
      this.s = new MulticastSocket(port);
      InetAddress group = InetAddress.getByName(host);
      this.s.joinGroup(group);
      this.clientThread = new Thread(this, "client-" + (++clientCounter));
      this.clientThread.setDaemon(false);
      this.clientThread.start();
      log.info("Client {} message listener started on port {}!", this.clientThread.getName(), port);
    }

    @Override
    public void run() {
      while (!isClosing && !Thread.currentThread().isInterrupted()) {
        log.info("Waiting for packets");
        try {
          DatagramPacket requestPacket = makeEmptyDatagramPacket();
          s.receive(requestPacket);
          log.info("Received packet from {}:{}", requestPacket.getAddress(), requestPacket.getPort());
          NumberMessage request = new NumberMessage(requestPacket.getData());
          log.info("Received message {}", request);
        }
        catch (IOException e) {
          if (!isClosing) {
            log.warn("error while handling packet", e);
          } else {
            log.trace("error while handling packet during shutdown", e);
          }
        }
      }
    }

    @Override
    public void close() throws IOException {
      this.isClosing = true;
      if (clientThread != null) clientThread.interrupt();
      if (s != null && !s.isClosed()) {
        log.info("Client {} closing", clientThread.getName());
        s.close();
      }
    }

    public final void join() throws InterruptedException {
      clientThread.join();
    }

  }

  private static class NumberMessage {

    private long number;

    public NumberMessage(long number) {
      this.number = number;
    }

    public NumberMessage(byte[] bytes) {
      ByteBuffer buffer = ByteBuffer.wrap(bytes);
      this.number = buffer.getLong();
    }
    public byte[] toByteArray() {
      ByteBuffer buffer = ByteBuffer.wrap(makeByteArray());
      buffer.putLong(number);
      buffer.flip();
      byte[] result = new byte[buffer.remaining()];
      buffer.get(result);
      return result;
    }

    @Override
    public String toString() {
      return "NumberMessage [number=" + number + "]";
    }

  }

  private static DatagramPacket makeEmptyDatagramPacket() {
    byte[] payload = makeByteArray();
    return new DatagramPacket(payload, payload.length);
  }

  private static DatagramPacket makeDatagramPacket(InetAddress address, int port, NumberMessage msg) {
    byte[] bytes = msg.toByteArray();
    return new DatagramPacket(bytes, bytes.length, address, port);
  }

  private static byte[] makeByteArray() {
    return new byte[1024];
  }

}
