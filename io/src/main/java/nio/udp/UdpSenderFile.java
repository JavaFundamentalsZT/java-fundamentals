package nio.udp;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;

public class UdpSenderFile extends UdpAddress {

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
        FileChannel fc = new FileInputStream(arg).getChannel();
        long bytesSent = fc.transferTo(0, fc.size(), channel);
        System.out.format("Datagram with %d bytes sent: File %s%n", bytesSent, arg);
      }
    }
    finally {
      channel.close();
    }
  }

}
