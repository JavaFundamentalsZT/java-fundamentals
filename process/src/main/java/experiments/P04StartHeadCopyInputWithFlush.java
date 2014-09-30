package experiments;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class P04StartHeadCopyInputWithFlush {

  public static void main(String[] args) throws Exception {
    ProcessBuilder builder =
        new ProcessBuilder("head", "-n 3");
    Process p = builder.start();
    //run in background
    //uses "functional interface" feature from Java 8
    new Thread(() ->  copyStreamWithFlush(
      System.in, p.getOutputStream(), p)).start();
    IOUtils.copy(p.getInputStream(), System.out);
    //this will cause the System.in copier to close
    System.in.close();
  }

  private static void copyStreamWithFlush(InputStream is, OutputStream os, Process p) {
    try {
      int i;
      while ((i = is.read()) != -1 && p.isAlive()) {
        os.write(i);
        os.flush();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
