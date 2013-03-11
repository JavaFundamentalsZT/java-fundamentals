package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;

public class TransferFrom {

  public static void main(String[] args) throws IOException {
    RandomAccessFile fromFile = null;
    RandomAccessFile toFile = null;
    try {
      fromFile = new RandomAccessFile("fromFile.txt", "rw");
      toFile = new RandomAccessFile("toFile.txt", "rw");

      FileChannel fromChannel = fromFile.getChannel();
      FileChannel toChannel = toFile.getChannel();

      toChannel.transferFrom(fromChannel, 0, fromChannel.size());
    }
    finally {
      IOUtils.closeQuietly(fromFile);
      IOUtils.closeQuietly(toFile);
    }
  }

}
