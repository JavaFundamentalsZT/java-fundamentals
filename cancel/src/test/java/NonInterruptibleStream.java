import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;


public class NonInterruptibleStream extends FilterInputStream {

  protected NonInterruptibleStream(InputStream in) {
    super(in);
  }
  
  @Override
  public int read() throws IOException {
    while (true) {
      try {
        return super.read();
      }
      catch (InterruptedIOException e) {
      }
    }
  }

}
