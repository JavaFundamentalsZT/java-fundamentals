package nio.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class UdpExample {

  public static void main(String[] args) throws IOException {
  }

  private static void send() throws IOException {
    String data = "Hello";
    ByteBuffer buf = ByteBuffer.wrap(data.getBytes());
    DatagramChannel channel = DatagramChannel.open();
    {
      int bytesSent = channel.send(buf, new InetSocketAddress("localhost", 9999));
    }
    {
      channel.connect(new InetSocketAddress("localhost", 9999));
      int bytesSent = channel.write(buf);
    }
  }

  private static void receive() throws IOException {
    ByteBuffer buf = ByteBuffer.allocate(48);
    buf.clear();
    DatagramChannel channel = DatagramChannel.open();
    channel.socket().bind(new InetSocketAddress(9999));
    {
      SocketAddress sa = channel.receive(buf);
    }
    {
      channel.connect(new InetSocketAddress("localhost", 8888));
      int bytesReceived = channel.read(buf);
    }
  }

  private static void receiveSelect(int... ports) throws IOException {
    Selector selector = Selector.open();
    for (int port : ports) {
      DatagramChannel dc = DatagramChannel.open();
      dc.configureBlocking(false);
      dc.bind(new InetSocketAddress(port));
      dc.register(selector, SelectionKey.OP_READ);
    }
    while (true) {
      selector.select();
      for (Iterator<SelectionKey> it =
          selector.selectedKeys().iterator(); it.hasNext();) {
        SelectionKey key = it.next();
        DatagramChannel dc = (DatagramChannel) key.channel();
        handle(dc);
        it.remove();
      }
    }
  }

  private static void handle(DatagramChannel channel) throws IOException {
    ByteBuffer buf = ByteBuffer.allocate(1024);
    InetSocketAddress addr = (InetSocketAddress) channel.getLocalAddress();
    System.out.format("Channel %s is ready for reading.%n", channel);

    SocketAddress sa = channel.receive(buf);
    buf.flip();
    System.out.format("Received a datagram with %d bytes on port %d from %s:%n", buf.limit(), addr.getPort(), sa);
    while (buf.hasRemaining()){
      System.out.print((char) buf.get());
    }
    System.out.println();
    buf.clear();
  }

}
