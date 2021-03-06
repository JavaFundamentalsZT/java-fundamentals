package org.zeroturnaround.jf2012.concurrency.counter.counter;

public class VolatilePrimitiveCounter implements Counter {

  private volatile int counter;

  @Override
  public void run() {
    counter++;
  }

  @Override
  public int getCount() {
    return counter;
  }

}
