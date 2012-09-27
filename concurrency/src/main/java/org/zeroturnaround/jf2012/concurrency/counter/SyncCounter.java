package org.zeroturnaround.jf2012.concurrency.counter;

public class SyncCounter implements Counter {

  private int counter;

  @Override
  public synchronized void run() {
    counter++;
  }

  @Override
  public int getCount() {
    return counter;
  }

}
