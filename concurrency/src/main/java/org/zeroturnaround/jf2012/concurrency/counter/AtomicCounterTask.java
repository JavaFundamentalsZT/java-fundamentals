package org.zeroturnaround.jf2012.concurrency.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounterTask implements Runnable {

  private final AtomicInteger counter = new AtomicInteger();

  @Override
  public void run() {
    counter.incrementAndGet();
  }

  @Override
  public String toString() {
    return "AtomicCounter " + counter;
  }

}
