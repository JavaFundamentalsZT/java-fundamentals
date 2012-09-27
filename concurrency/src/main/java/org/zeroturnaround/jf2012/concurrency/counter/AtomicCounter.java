package org.zeroturnaround.jf2012.concurrency.counter;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter implements Counter {

  private final AtomicInteger counter = new AtomicInteger();

  @Override
  public void run() {
    counter.incrementAndGet();
  }

  @Override
  public int getCount() {
    return counter.get();
  }

}
