package org.zeroturnaround.jf2012.concurrency.counter;

public class SimpleCounter implements Counter {

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
