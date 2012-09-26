package org.zeroturnaround.jf2012.concurrency.counter;

public class UnsafeCounterTask implements Runnable {

  private int counter;

  @Override
  public void run() {
    counter++;
  }

  @Override
  public String toString() {
    return "UnsafeCounter " + counter;
  }

}
