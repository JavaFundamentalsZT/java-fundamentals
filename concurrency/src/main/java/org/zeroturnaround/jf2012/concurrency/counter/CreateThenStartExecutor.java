package org.zeroturnaround.jf2012.concurrency.counter;

public class CreateThenStartExecutor implements ConcurrentExecutor {

  @Override
  public void invoke(Runnable task, int threads) throws InterruptedException {
    // Create threads
    Thread[] ts = new Thread[threads];
    for (int i = 0; i < ts.length; i++)
      ts[i] = new Thread(task);
    // Start threads
    for (int i = 0; i < ts.length; i++)
      ts[i].start();
    // Wait until all threads have finished
    for (int i = 0; i < ts.length; i++) {
      ts[i].join();
    }
  }

}
