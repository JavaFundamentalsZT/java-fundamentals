package org.zeroturnaround.jf2012.concurrency.cancel.lock;

public class Unlock2 {

  public static void main(String[] args) throws Exception {
    Object lock = new Object();
    Thread thread1 = new KeepLock(lock);
    Thread thread2 = new KeepLock(lock);
    thread1.start();
    Thread.sleep(1000);
    thread2.start();
    System.out.println("Interrupting...");
    thread1.interrupt();
    thread2.interrupt();
  }

}
