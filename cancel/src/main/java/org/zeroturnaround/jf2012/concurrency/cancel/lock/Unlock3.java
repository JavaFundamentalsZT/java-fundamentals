package org.zeroturnaround.jf2012.concurrency.cancel.lock;

public class Unlock3 {

  public static void main(String[] args) throws Exception {
    Object lock = new Object();
    {
      Thread thread1 = new KeepLock(lock);
      thread1.setDaemon(true);
      thread1.start();
      Thread.sleep(1000);
    }
    {
      Thread thread2 = new KeepLock(lock);
      thread2.start();
      Thread.sleep(1000);
      System.out.println("Interrupting...");
      thread2.interrupt();
    }
  }

}
