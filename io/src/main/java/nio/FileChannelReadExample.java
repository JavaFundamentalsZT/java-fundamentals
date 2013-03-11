package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;

public class FileChannelReadExample {

  public static void main(String[] args) throws IOException {
    FileChannel fc = new RandomAccessFile("in.txt", "r").getChannel();
    try {
      ByteBuffer buf = ByteBuffer.allocate(48);
      while (fc.read(buf) != -1) {
        buf.flip();
        while (buf.hasRemaining())
          System.out.print((char) buf.get());
        buf.clear();
      }
    }
    finally { fc.close(); }
  }

}
