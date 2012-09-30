package org.zeroturnaround.jf2012.concurrency.counter.counter;

public interface Counter extends Runnable {

  /**
   * Increase the counter value.
   */
  void run();

  /**
   * @return current counter value.
   */
  int getCount();

}
