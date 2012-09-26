package org.zeroturnaround.jf2012.concurrency.counter;

public class SyncCounterTask implements Runnable {

  private int counter;

  @Override
  public synchronized void run() {
    counter++;
  }

  @Override
  public synchronized String toString() {
    return "SyncCounter " + counter;
  }

}
