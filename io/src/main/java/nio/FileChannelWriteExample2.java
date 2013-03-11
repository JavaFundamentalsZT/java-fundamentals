package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelWriteExample2 {

  public static void main(String[] args) throws IOException {
    FileChannel fc = new RandomAccessFile("out.txt", "rw").getChannel();
    try {
      String data = "Hello";
      ByteBuffer buf = ByteBuffer.wrap(data.getBytes());
      fc.write(buf);
    }
    finally { fc.close(); }
  }

}
