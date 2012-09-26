package org.zeroturnaround.jf2012.concurrency.counter;

import java.util.concurrent.CountDownLatch;

public class CountdownExecutor implements ConcurrentExecutor {

  @Override
  public void invoke(Runnable task, int threads) throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    CountdownAction action = new CountdownAction(task, latch);
    // Create and start threads
    Thread[] ts = new Thread[threads];
    for (int i = 0; i < ts.length; i++) {
      ts[i] = new Thread(action);
      ts[i].start();
    }
    // Start action
    latch.countDown();
    // Wait until all threads have finished
    for (int i = 0; i < ts.length; i++) {
      ts[i].join();
    }
  }

}
