package console;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;

public class SumTest2 extends Assert {

  @Test
  public void test() throws Exception {
    final PipedOutputStream pos = new PipedOutputStream();
    InputStream in = new PipedInputStream(pos);
    new Thread() {
      @Override
      public void run() {
        try {
          pos.write("3\n4\n".getBytes());
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    }.start();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(baos);
    Sum.sum(in, out);
    String output = new String(baos.toByteArray());
    assertEquals("7", output);
  }

}
