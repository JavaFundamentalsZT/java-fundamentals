package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * InputStream wrapper which can be interrupted.
 *
 * @author Rein Raudjärv
 */
public class BufferedInterruptibleInputStream extends InputStream implements ThreadFactory, Callable<Integer> {

  private final InputStream in;

  private final ExecutorService service = Executors.newSingleThreadExecutor(this);

  public BufferedInterruptibleInputStream(InputStream in) {
    this.in = in;
  }

  @Override
  public int read() throws IOException {
    // Invoke the operation using separate thread to enable interrupting this thread
    try {
      return service.submit(this).get();
    }
    catch (InterruptedException e) {
      throw new InterruptedIOException(e.getMessage());
    }
    catch (ExecutionException e) {
      throw new IOException(e.getCause());
    }
  }

  @Override
  public void close() throws IOException {
    try {
      service.shutdownNow();
    }
    finally {
      in.close();
    }
  }

  public Thread newThread(Runnable r) {
    // Reader thread may get blocked and should not ruin stopping the JVM.
    Thread t = new Thread(r);
    t.setDaemon(true);
    return t;
  }

  public Integer call() throws Exception {
    return in.read();
  }

}
