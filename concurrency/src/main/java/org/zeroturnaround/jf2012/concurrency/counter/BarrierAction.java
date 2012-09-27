package org.zeroturnaround.jf2012.concurrency.counter;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierAction implements Runnable {

  private final Runnable task;
  private final CyclicBarrier barrier;

  public BarrierAction(Runnable task, CyclicBarrier barrier) {
    this.task = task;
    this.barrier = barrier;
  }

  @Override
  public void run() {
    try {
      barrier.await();
    }
    catch (InterruptedException e) {
      // Restore the interrupt status
      Thread.currentThread().interrupt();
    }
    catch (BrokenBarrierException e) {
      e.printStackTrace();
    };
    task.run();
  }

}
