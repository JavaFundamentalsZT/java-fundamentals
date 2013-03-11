package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;

public class FileChannelWriteExample {

  public static void main(String[] args) throws IOException {
    FileChannel fc = new RandomAccessFile("out.txt", "rw").getChannel();
    try {
      String data = "Hello";
      ByteBuffer buf = ByteBuffer.allocate(48);
      buf.clear();
      buf.put(data.getBytes());
      buf.flip();
      fc.write(buf);
    }
    finally { fc.close(); }
  }

}
