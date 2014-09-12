package autoclose;

import java.io.IOException;
import java.io.OutputStream;

public class AutoCloseErrors {

  public static void main(String[] args) {
    try (OutputStream out = new FailingOutputStream();)
    {
      out.write(1);
    }
    catch (IOException e) {
      System.out.format("No of supressed exceptions: %d%n", e.getSuppressed().length);
      System.out.println("Enjoy the stacktrace:");
      e.printStackTrace();
    }
  }

  private static class FailingOutputStream extends OutputStream {
    public FailingOutputStream() throws IOException {
      //throw new IOException("init failed");
    }

    @Override
    public void write(int b) throws IOException {
      //throw new IOException("write failed");
    }

    @Override
    public void close() throws IOException {
      //throw new IOException("close failed");
    }
  }

}
