package nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TcpReceiver extends TcpAddress {

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
    
    while (true) {
      final SocketChannel channel = serverChannel.accept();
      new Thread() {
        public void run() {
          try {
            handle(channel);
          }
          catch (IOException e) {
            e.printStackTrace();
          }
        }
      }.start();
    }
  }

  private static void handle(SocketChannel channel) throws IOException {
    ByteBuffer buf = ByteBuffer.allocate(1024);
    SocketAddress addr = channel.getRemoteAddress();
    System.out.format("Socket from %s accepted.%n", addr);
    try {
      int bytesRead;
      while ((bytesRead = channel.read(buf)) > 0) {
        buf.flip();
        System.out.format("%d bytes received from %s:%n", bytesRead, addr);
        while (buf.hasRemaining()){
          System.out.print((char) buf.get());
        }
        System.out.println();
        buf.clear();
      }
    }
    finally {
      channel.close();
      System.out.format("Socket from %s closed.%n", addr);
    }
  }
  
}
