package org.zeroturnaround.jf2012.concurrency.counter.counter;

public class SyncWrapperCounter implements Counter {

  private Integer counter = 0;

  @Override
  public synchronized void run() {
    counter++;
  }

  @Override
  public int getCount() {
    return counter;
  }

}
