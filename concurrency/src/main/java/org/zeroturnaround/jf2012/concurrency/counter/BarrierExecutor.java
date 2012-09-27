package org.zeroturnaround.jf2012.concurrency.counter;

import java.util.concurrent.CyclicBarrier;

public class BarrierExecutor extends CreateAndStartExecutor {

  @Override
  public void invoke(Runnable task, int threads, int times) throws InterruptedException {
    // Wrap the task such that it will synchronize between all parties
    CyclicBarrier barrier = new CyclicBarrier(threads);
    BarrierAction action = new BarrierAction(task, barrier);
    super.invoke(action, threads, times);
  }

}
