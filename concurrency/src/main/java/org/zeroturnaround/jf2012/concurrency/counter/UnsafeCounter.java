package org.zeroturnaround.jf2012.concurrency.counter;

public class UnsafeCounter {

  private static int counter;

  public static int increment() {
    return counter++;
  }

  public static int get() {
    return counter;
  }

}
