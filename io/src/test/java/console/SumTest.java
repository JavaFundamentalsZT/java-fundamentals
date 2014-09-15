package console;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Test;

public class SumTest extends Assert {

  @Test
  public void test() throws Exception {
    PipedOutputStream pos = new PipedOutputStream();
    InputStream in = new PipedInputStream(pos);
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(baos);
    
    pos.write("3\n4\n".getBytes());
    
    Sum.sum(in, out);
    String output = new String(baos.toByteArray());
    
    assertEquals("7", output);
  }

}
