import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.apache.commons.io.input.ClosedInputStream;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.jf2012.concurrency.cancel.io.InterruptibleInputStream;


public class NonblockingInputStreamTest extends Assert {

  private static final Logger log = LoggerFactory.getLogger(NonblockingInputStreamTest.class);

  @Test
  public void testSingleByte() throws IOException {
    InputStream in = new InterruptibleInputStream(new ByteArrayInputStream(new byte[] { 100 }));
    try {
      int b = in.read();
      assertEquals(100, b);
    }
    finally {
      in.close();
    }
  }

  @Test
  public void testEmpty() throws IOException {
    InputStream in = new InterruptibleInputStream(new ByteArrayInputStream(new byte[0]));
    try {
      int b = in.read();
      assertEquals(-1, b);
    }
    finally {
      in.close();
    }
  }

  @Test
  public void testClosed() throws IOException {
    InputStream in = new InterruptibleInputStream(new ClosedInputStream());
    try {
      int b = in.read();
      assertEquals(-1, b);
    }
    finally {
      in.close();
    }
  }
  
//  @Test
//  public void testInterrupt() throws IOException {
//    PipedInputStream pipe = new PipedInputStream(new PipedOutputStream());
//    try {
//      pipe.read();
//    }
//  }

  //  @Test
  //  public void test2() throws IOException {
  //    InputStream in = new ByteArrayInputStream(new byte[] { 100 });
  //    int b = in.read();
  //    b = in.read();
  //    assertEquals(100, b);
  //  }

  @Test
  public void test2() throws IOException {
    InputStream in = new ClosedInputStream();
    int b = in.read();
    assertEquals(-1, b);
  }
  
}
