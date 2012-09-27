package org.zeroturnaround.jf2012.concurrency.counter;

import java.util.concurrent.CountDownLatch;

public class CountdownAction implements Runnable {

  private final Runnable task;
  private final CountDownLatch latch;

  public CountdownAction(Runnable task, CountDownLatch latch) {
    this.task = task;
    this.latch = latch;
  }

  @Override
  public void run() {
    try {
      latch.await();
    }
    catch (InterruptedException e) {
      // Restore the interrupt status
      Thread.currentThread().interrupt();
    };
    task.run();
  }

}
