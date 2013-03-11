package nio.udp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class UdpReceiverSelector extends UdpAddress {

  public static void main(String[] args) throws IOException {
    if (args.length == 0)
      throw new IllegalArgumentException("Ports must be provided as input arguments.");

    Selector selector = Selector.open();
    
    for (String arg : args) {
      int port = Integer.parseInt(arg);
      DatagramChannel channel = DatagramChannel.open();
      channel.configureBlocking(false);
      channel.bind(new InetSocketAddress(port));
      SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
      System.out.format("Waiting datagrams on port %d, %s...%n", port, key);
    }
    
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.out.println("Stopped waiting datagrams.");
      }
    });
    
    ByteBuffer buf = ByteBuffer.allocate(1024);
    while (true) {
      System.out.println("Waiting for any channel to receive data...");
      selector.select();
      Set<SelectionKey> selectedKeys = selector.selectedKeys();
      for (Iterator<SelectionKey> it = selectedKeys.iterator(); it.hasNext();) {
        SelectionKey key = it.next();

        DatagramChannel channel = (DatagramChannel) key.channel();
        InetSocketAddress addr = (InetSocketAddress) channel.getLocalAddress();
        System.out.format("Channel %s is ready for reading.%n", channel);
        
        SocketAddress sa = channel.receive(buf);
        buf.flip();
        System.out.format("Received a datagram with %d bytes on port %d from %s:%n", buf.limit(), addr.getPort(), sa);
        while (buf.hasRemaining()){
          System.out.print((char) buf.get());
        }
        System.out.println();
        buf.clear();
        
        // Otherwise the key remains in the set
        it.remove();
      }
    }
  }
  
}
