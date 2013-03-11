import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class BlockingInputStream extends InputStream {

  private final BlockingQueue<Integer> buffer = new LinkedBlockingDeque<Integer>();

  private final boolean interruptible;

  public BlockingInputStream(boolean interruptible) {
    this.interruptible = interruptible; 
  }

  @Override
  public int read() throws IOException {
    while (true) {
      try {
        return buffer.take();
      }
      catch (InterruptedException e) {
        if (interruptible)
          throw new InterruptedIOException(e.getMessage());
        else
          Thread.currentThread().interrupt();
      }
    }
  }

}
