package processbuilder.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;




public class CopyInputSmartJavaExample {

  public static void main(String[] args) throws Exception {
    // Read and output 3 lines
    ProcessBuilder builder = new ProcessBuilder("head", "-n", "3");
    final Process process = builder.start();

    new Thread() {
      public void run() {
        try {
          IOUtils.copy(process.getInputStream(), System.out);
        }
        catch (IOException e) {
        }
      };
    }.start();
    
    new Thread() {
      {
        setDaemon(true);
      }
      public void run() {
        try {
          flushyCopy(System.in, process.getOutputStream());
        }
        catch (IOException e) {
        }
      };
    }.start();

    process.waitFor();
    System.out.println("Process finished.");
  }
  
  private static void flushyCopy(InputStream in, OutputStream out) throws IOException {
    System.out.println("Flushy copy " + in + " to " + out + " started");
    try {
      int i = 0;
      while ((i = in.read()) != -1) {
        out.write(i);
        out.flush();
      }
    }
    finally {
      System.out.println("Flushy copy " + in + " to " + out + " finished");
    }
  }
  
}
