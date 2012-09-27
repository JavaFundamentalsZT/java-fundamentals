package org.zeroturnaround.jf2012.concurrency.cancel.lock;

import java.util.concurrent.locks.ReentrantLock;

public class Unlock4 {

  public static void main(String[] args) throws Exception {
    ReentrantLock lock = new ReentrantLock();
    {
      Thread thread1 = new KeepLockInterruptibly(lock);
      thread1.setDaemon(true);
      thread1.start();
      Thread.sleep(1000);
    }
    {
      Thread thread2 = new KeepLockInterruptibly(lock);
      thread2.start();
      Thread.sleep(1000);
      System.out.println("Interrupting...");
      thread2.interrupt();
    }
  }

}
