package org.zeroturnaround.jf2012.concurrency.counter.counter;

public class SimplePrimitiveCounter implements Counter {

  private int counter;

  @Override
  public void run() {
    counter++;
  }

  @Override
  public int getCount() {
    return counter;
  }

}
