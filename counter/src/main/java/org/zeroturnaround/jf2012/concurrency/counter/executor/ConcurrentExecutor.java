package org.zeroturnaround.jf2012.concurrency.counter.executor;

public interface ConcurrentExecutor {

  /**
   * Invoke a task using the given number of threads. 
   */
  void invoke(Runnable task, int threads, int times) throws InterruptedException;

}
