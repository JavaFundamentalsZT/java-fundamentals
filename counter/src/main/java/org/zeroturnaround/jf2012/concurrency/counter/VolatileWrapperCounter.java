package org.zeroturnaround.jf2012.concurrency.counter;

public class VolatileWrapperCounter implements Counter {

  private volatile Integer counter = 0;

  @Override
  public void run() {
    counter++;
  }

  @Override
  public int getCount() {
    return counter;
  }

}
