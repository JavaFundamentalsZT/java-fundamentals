package org.zeroturnaround.jf2012.concurrency.cancel.lock;

public class Unlock1 {

  public static void main(String[] args) throws Exception {
    Object lock = new Object();
    Thread t = new KeepLock(lock);
    t.start();
    Thread.sleep(1000);
    System.out.println("Interrupting...");
    t.interrupt();
  }

}
