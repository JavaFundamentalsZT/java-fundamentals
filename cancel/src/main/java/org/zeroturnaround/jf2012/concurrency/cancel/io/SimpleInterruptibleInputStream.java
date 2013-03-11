package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * InputStream wrapper which can be interrupted.
 *
 * @author Rein Raudjärv
 */
public class SimpleInterruptibleInputStream extends InputStream implements Callable<Integer> {
  final InputStream in;
  final ExecutorService service = Executors.newSingleThreadExecutor();
  public SimpleInterruptibleInputStream(InputStream in) {
    this.in = in;
  }
  @Override
  public int read() throws IOException {
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
  public Integer call() throws Exception {
    return in.read();
  }
  

  public int read(byte[] b, int off, int len) throws IOException {
    // Disable buffering
    return super.read(b, off, 1);
  }
  public void close() throws IOException {
    service.shutdownNow();
    in.close();
  }
  public Thread newThread(Runnable r) {
    Thread t = new Thread(r);
    t.setDaemon(true);
    return t;
  }

}
