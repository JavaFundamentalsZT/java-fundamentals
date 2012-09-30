package org.zeroturnaround.jf2012.concurrency.counter.executor;


public class CreateAndStartExecutor extends AbstractConcurrentExecutor {

  @Override
  public void invoke(Runnable task, int threads) throws InterruptedException {
    // Create and start threads
    Thread[] ts = new Thread[threads];
    for (int i = 0; i < ts.length; i++) {
      ts[i] = new Thread(task);
      ts[i].start();
    }
    // Wait until all threads have finished
    for (int i = 0; i < ts.length; i++) {
      ts[i].join();
    }
  }

}
