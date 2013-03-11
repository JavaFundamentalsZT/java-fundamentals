package nio.tcp;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class TcpSenderStdIn extends TcpAddress {

  public static void main(String[] args) throws IOException {
    System.out.format("Streaming standard input stream onto port %d...%n", receiverPort);
    
    FileChannel fc = new FileInputStream(FileDescriptor.in).getChannel();
    
    SocketChannel channel = SocketChannel.open();
    try {
      channel.connect(new InetSocketAddress(host, receiverPort));
      
      if (senderPort != null)
        channel.bind(new InetSocketAddress(senderPort));

      ByteBuffer buf = ByteBuffer.allocate(1);
      while (true) {
        fc.read(buf);
        buf.flip();
        channel.write(buf);
        buf.clear();
      }
    }
    finally {
      channel.close();
    }
  }

}
