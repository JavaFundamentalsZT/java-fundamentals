package nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TcpSender extends TcpAddress {

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
        ByteBuffer buf = ByteBuffer.wrap(arg.getBytes());
        int bytesSent = channel.write(buf);
        System.out.format("Packet with %d bytes sent: %s%n", bytesSent, arg);
      }
    }
    finally {
      channel.close();
    }
  }

}
