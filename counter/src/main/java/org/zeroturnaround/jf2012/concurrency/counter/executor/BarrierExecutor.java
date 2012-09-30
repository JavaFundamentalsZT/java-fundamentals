package org.zeroturnaround.jf2012.concurrency.counter.executor;

import java.util.concurrent.CyclicBarrier;

public class BarrierExecutor extends CreateAndStartExecutor {

  private static final int CYCLE_AFTER_EACH = 1000;
  
  @Override
  public void invoke(Runnable task, int threads, int times) throws InterruptedException {
    // Wrap the task such that it will synchronize between all parties
    CyclicBarrier barrier = new CyclicBarrier(threads);
    Runnable cycle = new BarrierAction(new Repeat(task, CYCLE_AFTER_EACH), barrier);
    super.invoke(cycle, threads, times / CYCLE_AFTER_EACH);
  }

}
