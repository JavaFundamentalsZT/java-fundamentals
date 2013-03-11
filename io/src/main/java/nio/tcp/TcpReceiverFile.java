package nio.tcp;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TcpReceiverFile extends TcpAddress {

  public static void main(String[] args) throws IOException {
    System.out.format("Waiting sockets on port %d...%n", receiverPort);
    
    ServerSocketChannel serverChannel = ServerSocketChannel.open();

    serverChannel.bind(new InetSocketAddress(receiverPort));

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.out.format("Stopped waiting sockets on port %d.%n", receiverPort);
      }
    });
    
    FileChannel fc = new RandomAccessFile("out", "rw").getChannel();
    fc.truncate(0);
    int pos = 0;
    
    while (true) {
      SocketChannel channel = serverChannel.accept();
      SocketAddress addr = channel.getRemoteAddress();
      System.out.format("Socket from %s accepted.%n", addr);
      try {
        long bytesRead;
        while ((bytesRead = fc.transferFrom(channel, pos, 1024)) > 0) {
          pos += bytesRead;
          System.out.format("%d bytes received from %s:%n", bytesRead, addr);
        }
      }
      finally {
        channel.close();
        System.out.format("Socket from %s closed.%n", addr);
      }
    }
  }
  
}
