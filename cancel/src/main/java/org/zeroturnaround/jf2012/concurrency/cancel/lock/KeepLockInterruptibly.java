package org.zeroturnaround.jf2012.concurrency.cancel.lock;

import java.util.concurrent.locks.ReentrantLock;

public class KeepLockInterruptibly extends Thread {

  protected final ReentrantLock lock;

  public KeepLockInterruptibly(ReentrantLock lock) {
    this.lock = lock;
  }

  public void run() {
    try {
      System.out.printf("Locking on %s...%n", lock);
      lock.lockInterruptibly();
      try {
        System.out.printf("Locked on %s.%n", lock);
        while (true) {
          Thread.sleep(1000); 
        }
      }
      finally {
        System.out.printf("Unlocking %s...%n", lock);
        lock.unlock();
        System.out.printf("Unlocked %s.%n", lock);
      }
    }
    catch (InterruptedException e) {
      System.out.println("Interrupted.");
    }
  }
  
}
