package nio.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpSender extends UdpAddress {

  public static void main(String[] args) throws IOException {
    if (args.length == 0)
      throw new IllegalArgumentException("Provide some arguments.");

    System.out.format("Sending datagrams onto port %d...%n", receiverPort);
    final DatagramChannel channel = DatagramChannel.open();
    
    if (senderPort != null)
      channel.bind(new InetSocketAddress(senderPort));
    
    try {
      for (String arg : args) {
        ByteBuffer buf = ByteBuffer.wrap(arg.getBytes());
        int bytesSent = channel.send(buf, new InetSocketAddress(host, receiverPort));
        System.out.format("Datagram with %d bytes sent: %s%n", bytesSent, arg);
      }
    }
    finally {
      channel.close();
    }
  }

}
