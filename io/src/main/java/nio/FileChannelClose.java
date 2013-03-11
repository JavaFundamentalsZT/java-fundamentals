package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelClose {

  public static void main(String[] args) throws IOException {
    // Closing RandomAccessFile closes the FileChannel
    {
      RandomAccessFile aFile = new RandomAccessFile("data.txt", "rw");
      FileChannel channel = aFile.getChannel();
      aFile.close();
      System.out.format("is open: %b%n", channel.isOpen()); // false
    }
    // Closing FileInputStream closes the FileChannel
    {
      FileInputStream aFile = new FileInputStream("data.txt");
      FileChannel channel = aFile.getChannel();
      aFile.close();
      System.out.format("is open: %b%n", channel.isOpen()); // false
    }
    // Closing FileOutputStream closes the FileChannel
    {
      FileOutputStream aFile = new FileOutputStream("data.txt");
      FileChannel channel = aFile.getChannel();
      aFile.close();
      System.out.format("is open: %b%n", channel.isOpen()); // false
    }

    // Closing FileChannel closes the RandomAccessFile
    {
      RandomAccessFile aFile = new RandomAccessFile("data.txt", "rw");
      FileChannel channel = aFile.getChannel();
      channel.close();
      try {
        aFile.read();
      }
      catch (IOException e) {
        System.out.println("closed");
      }
    }
    // Closing FileChannel closes the FileInputStream
    {
      FileInputStream aFile = new FileInputStream("data.txt");
      FileChannel channel = aFile.getChannel();
      channel.close();
      try {
        aFile.read();
      }
      catch (IOException e) {
        System.out.println("closed");
      }
    }
    // Closing FileChannel closes the FileInputStream
    {
      FileOutputStream aFile = new FileOutputStream("data.txt");
      FileChannel channel = aFile.getChannel();
      channel.close();
      try {
        aFile.write(0);
      }
      catch (IOException e) {
        System.out.println("closed");
      }
    }
  }

}
