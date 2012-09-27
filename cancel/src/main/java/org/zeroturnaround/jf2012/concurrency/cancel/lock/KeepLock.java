package org.zeroturnaround.jf2012.concurrency.cancel.lock;

public class KeepLock extends Thread {

  protected final Object lock;

  public KeepLock(Object lock) {
    this.lock = lock;
  }

  public void run() {
    try {
      System.out.printf("Locking on %s...%n", lock);
      synchronized (lock) {
        System.out.printf("Locked on %s.%n", lock);
        while (true) {
          Thread.sleep(1000); 
        }
      }
    }
    catch (InterruptedException e) {
      System.out.println("Interrupted.");
    }
    finally {
      System.out.printf("Unlocked %s.%n", lock);
    }
  }

}
