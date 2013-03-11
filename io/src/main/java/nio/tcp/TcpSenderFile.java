package nio.tcp;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class TcpSenderFile extends TcpAddress {

  public static void main(String[] args) throws IOException {
    if (args.length == 0)
      throw new IllegalArgumentException("Provide some arguments.");

    System.out.format("Sending packets onto port %d...%n", receiverPort);
    
    SocketChannel channel = SocketChannel.open();
    try {
      channel.connect(new InetSocketAddress(host, receiverPort));
      
      if (senderPort != null)
        channel.bind(new InetSocketAddress(senderPort));

      for (String arg : args) {
        FileChannel fc = new FileInputStream(arg).getChannel();
        long bytesSent = fc.transferTo(0, fc.size(), channel);
        System.out.format("%d bytes sent: File %s%n", bytesSent, arg);
      }
    }
    finally {
      channel.close();
    }
  }

}
