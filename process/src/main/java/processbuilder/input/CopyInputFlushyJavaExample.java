package processbuilder.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;




public class CopyInputFlushyJavaExample {

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

    // process.getOutputStream() is buffered so we need a lot of data to actually cause it to be flushed
    try {
//      IOUtils.copy(System.in, process.getOutputStream());
      flushyCopy(System.in, process.getOutputStream());
    }
    catch (IOException e) {
    }

    process.waitFor();
    System.out.println("Process finished.");
  }
  
  private static void flushyCopy(InputStream in, OutputStream out) throws IOException {
    System.out.println("Flushy copy " + in + " to " + out + " started");
    int i;
    while ((i = in.read()) != -1) {
      out.write(i);
      out.flush();
    }
    System.out.println("Flushy copy " + in + " to " + out + " finished");
  }
  
}
