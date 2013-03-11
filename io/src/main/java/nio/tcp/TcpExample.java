package nio.tcp;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TcpExample {

  public static void serverSocket() throws IOException {
    ServerSocketChannel ssc = ServerSocketChannel.open();
    ssc.bind(new InetSocketAddress(80));
    ssc.socket().bind(new InetSocketAddress(80));
    while (true) {
      SocketChannel sc = ssc.accept();
      handle(sc);
    }
  }

  private static void handle(SocketChannel sc) {
  }

  public static void read() throws IOException {
    // Connect
    SocketChannel sc = SocketChannel.open();
    try {
      sc.connect(new InetSocketAddress("localhost", 80));

      // Read to file
      FileChannel fc = new RandomAccessFile("out", "rw").getChannel();
      try {
        int pos = 0; long bytesRead;
        while ((bytesRead = fc.transferFrom(sc, pos, 4096)) > 0)
          pos += bytesRead;
      }
      finally {
        fc.close();
      }

      // Read and print out
      ByteBuffer buf = ByteBuffer.allocate(4096);
      while (sc.read(buf) != -1) {
        buf.flip();
        while (buf.hasRemaining())
          System.out.print((char) buf.get());
        buf.clear();
      }
    }
    finally {
      sc.close();
    }
  }

  public static void selectExample() throws IOException {
    Selector selector = Selector.open();
    {
      ServerSocketChannel ssc = ServerSocketChannel.open();
      ssc.configureBlocking(false);
      ssc.bind(new InetSocketAddress(4000));
      ssc.register(selector, SelectionKey.OP_ACCEPT);
    }
    while (true) {
      selector.select();
      for (Iterator<SelectionKey> it =
          selector.selectedKeys().iterator(); it.hasNext();) {
        SelectionKey key = it.next();
        if (key.isReadable()) read(key);
        else if (key.isAcceptable()) accept(key);
        it.remove();
      }
    }
  }

  private static void read(SelectionKey key) throws IOException {
    SocketChannel sc = (SocketChannel) key.channel();
    ByteBuffer buf = ByteBuffer.allocate(1024);
    int bytesRead = sc.read(buf);
    if (bytesRead == -1)
      sc.close();
    else
      handle(buf);
  }

  private static void accept(SelectionKey key) throws IOException {
    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
    SocketChannel sc = ssc.accept();
    sc.configureBlocking(false);
    Selector selector = key.selector();
    sc.register(selector, SelectionKey.OP_READ);
  }

  private static void handle(ByteBuffer buf) {
  }


}
