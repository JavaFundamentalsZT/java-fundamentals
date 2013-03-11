package nio.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpReceiver extends UdpAddress {

  public static void main(String[] args) throws IOException {
    System.out.format("Waiting datagrams on port %d...%n", receiverPort);
    final DatagramChannel channel = DatagramChannel.open();

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.out.format("Stopped waiting datagrams on port %d.%n", receiverPort);
      }
    });
    
//    channel.socket().bind(new InetSocketAddress(port));
    channel.bind(new InetSocketAddress(receiverPort));
    
    ByteBuffer buf = ByteBuffer.allocate(1024);
    while (true) {
      SocketAddress sa = channel.receive(buf);
      buf.flip();
      System.out.format("Datagram with %d bytes received from %s:%n", buf.limit(), sa);
      while (buf.hasRemaining()){
        System.out.print((char) buf.get());
      }
      System.out.println();
      buf.clear();
    }
  }
  
}
