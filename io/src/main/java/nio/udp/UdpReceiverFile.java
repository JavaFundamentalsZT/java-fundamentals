package nio.udp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;

// Doesn't work as expected - no datagrams are received
public class UdpReceiverFile extends UdpAddress {

  public static void main(String[] args) throws IOException {
    if (args.length == 0)
      throw new IllegalArgumentException("File name must be provided.");
    if (senderPort == null)
      throw new IllegalArgumentException("-DsenderPort must be provided.");
    
    final FileChannel fc = new FileOutputStream(args[0]).getChannel();
    
    System.out.format("Waiting datagrams on port %d...%n", receiverPort);
    final DatagramChannel channel = DatagramChannel.open();

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.out.format("Stopped waiting datagrams on port %d.%n", receiverPort);
        close(channel);
        close(fc);
      }
    });
    
//    channel.socket().bind(new InetSocketAddress(port));
    channel.bind(new InetSocketAddress(receiverPort));
    
    channel.connect(new InetSocketAddress(senderPort));
    
    while (true) {
      long byesReceived = fc.transferFrom(channel, 0, 4);
      System.out.format("Datagram with %d bytes received.%n", byesReceived);
    }
  }

  private static void close(final Channel channel) {
    try {
      channel.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
  
}
