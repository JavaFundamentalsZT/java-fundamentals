package org.zeroturnaround.jf2012.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class SafeCounter {

  private static AtomicInteger counter = new AtomicInteger();

  public static int increment() {
    return counter.incrementAndGet();
  }

  public static int get() {
    return counter.get();
  }

}
