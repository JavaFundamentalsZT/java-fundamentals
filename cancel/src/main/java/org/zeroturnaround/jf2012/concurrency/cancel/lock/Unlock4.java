package org.zeroturnaround.jf2012.concurrency.cancel.lock;

import java.util.concurrent.locks.ReentrantLock;

public class Unlock4 {

  public static void main(String[] args) throws Exception {
    ReentrantLock lock = new ReentrantLock();

    Thread t1 = new KeepLockInterruptibly(lock);
    t1.setDaemon(true);
    t1.start();
    Thread.sleep(1000);

    Thread t2 = new KeepLockInterruptibly(lock);
    t2.start();
    Thread.sleep(1000);
    System.out.println("Interrupting...");
    t2.interrupt();
  }

}
