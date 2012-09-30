package org.zeroturnaround.jf2012.concurrency.counter.executor;


public abstract class AbstractConcurrentExecutor implements ConcurrentExecutor {

  @Override
  public void invoke(Runnable task, int threads, int times) throws InterruptedException {
    invoke(new Repeat(task, times), threads);
  }

  protected abstract void invoke(Runnable task, int threads) throws InterruptedException;

}
