package experiments;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class P03StartHeadCopyInput {

  public static void main(String[] args) throws Exception {
    ProcessBuilder builder =
      new ProcessBuilder("head", "-n", "3");
    Process p = builder.start();
    //run in background
    //uses "functional interface" feature from Java 8
    new Thread(() ->  copyStream(
      p.getInputStream(), System.out)).start();
    IOUtils.copy(System.in, p.getOutputStream());
  }

  //we need this to get rid of the IOException which Thread.run() does not like
  private static void copyStream(InputStream is, OutputStream os) {
    try {
      IOUtils.copy(is, os);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
