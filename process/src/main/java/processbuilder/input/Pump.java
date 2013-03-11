package processbuilder.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Pump extends Thread {

  private final InputStream in;
  private final OutputStream out;
  
  public Pump(InputStream in, OutputStream out) {
    this.in = in;
    this.out = out;
  }
  public void run() {
    System.out.format("Started to pump %s onto %s.%n", in, out);
    try {
      int i = 0;
      while ((i = in.read()) != -1) {
        out.write(i);
        out.flush();
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    System.out.format("Finished pumping %s onto %s.%n", in, out);
  }

}