package nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TcpReceiverSelector extends TcpAddress {

  public static void main(String[] args) throws IOException {
    System.out.format("Waiting sockets on port %d...%n", receiverPort);
    
    Selector selector = Selector.open();
    
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    serverChannel.configureBlocking(false);
    serverChannel.bind(new InetSocketAddress(receiverPort));
    serverChannel.register(selector, SelectionKey.OP_ACCEPT);

    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.out.format("Stopped waiting sockets on port %d.%n", receiverPort);
      }
    });
    
    ByteBuffer buf = ByteBuffer.allocate(64);
    while (true) {
      System.out.println("Waiting for any channel to receive data...");
      selector.select();
      Set<SelectionKey> selectedKeys = selector.selectedKeys();
      for (Iterator<SelectionKey> it = selectedKeys.iterator(); it.hasNext();) {
        SelectionKey key = it.next();

        if (key.isAcceptable()) {
          System.out.format("Channel %s is ready for accepting.%n", serverChannel);
          SocketChannel channel = serverChannel.accept();
          channel.configureBlocking(false);
          channel.register(selector, SelectionKey.OP_READ);
          InetSocketAddress addr = (InetSocketAddress) channel.getRemoteAddress();
          System.out.format("Socket from %s accepted.%n", addr);
        }
        else {
          SocketChannel channel = (SocketChannel) key.channel();
          InetSocketAddress addr = (InetSocketAddress) channel.getRemoteAddress();
          System.out.format("Channel %s is ready for reading.%n", channel);
          
          int bytesRead = channel.read(buf);
          if (bytesRead == -1) {
            buf.clear();
            // Otherwise it remains to read -1 bytes
            channel.close();
            System.out.format("Socket from %s closed.%n", addr);
          }
          else {
            buf.flip();
            System.out.format("Received %d bytes from %s:%n", bytesRead, addr);
            while (buf.hasRemaining()){
              System.out.print((char) buf.get());
            }
            System.out.println();
            buf.clear();
          }
        }
        
        // Otherwise the key remains in the set
        it.remove();
      }
    }
  }
  
}
