package processbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

  public class InheritIoExample {

  public static void main(String[] args) throws Exception {
    new Thread(new InputReader()).start();

    // Read and output 3 lines
    ProcessBuilder builder = new ProcessBuilder("head", "-n", "3");
    builder.inheritIO();
    Process process = builder.start();
    process.waitFor();
  }

  private static class InputReader implements Runnable {
    @Override
    public void run() {

      try {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Line: " + in.readLine());
        String line;
        while ((line = in.readLine()) != null) {
          System.out.println("Line: " + line);
        }

//        int a = System.in.read();
//        System.out.println("Read " + a);
//        System.out.println("Read " + new String(new byte[] { (byte) a }));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
