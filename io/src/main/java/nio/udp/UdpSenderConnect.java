package nio.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpSenderConnect extends UdpAddress {

  public static void main(String[] args) throws IOException {
    if (args.length == 0)
      throw new IllegalArgumentException("Provide some arguments.");

    System.out.format("Sending datagrams onto port %d...%n", receiverPort);
    final DatagramChannel channel = DatagramChannel.open();

    if (senderPort != null)
      channel.bind(new InetSocketAddress(senderPort));
    
    channel.connect(new InetSocketAddress(host, receiverPort));
    
    try {
      for (String arg : args) {
        ByteBuffer buf = ByteBuffer.wrap(arg.getBytes());
        int bytesSent = channel.write(buf);
        System.out.format("Datagram with %d bytes sent: %s%n", bytesSent, arg);
      }
    }
    finally {
      channel.close();
    }
  }

}
