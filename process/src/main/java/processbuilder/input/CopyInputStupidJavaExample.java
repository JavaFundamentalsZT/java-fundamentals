package processbuilder.input;

import java.io.IOException;

import org.apache.commons.io.IOUtils;




public class CopyInputStupidJavaExample {

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
      IOUtils.copy(System.in, process.getOutputStream());
    }
    catch (IOException e) {
    }

    process.waitFor();
    System.out.println("Process finished.");
  }
  
}
