package file.locks;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class FileLockExample {
  public static void main(String[] args) throws IOException {
    File lockFile = null; // some file to lock
    try (RandomAccessFile raf = new RandomAccessFile(lockFile, "rw");
         FileLock lock = raf.getChannel().lock()) { // #lock blocks!
      System.out.println("Locked: " + lockFile + " with " + lock);
    }
  }
}
