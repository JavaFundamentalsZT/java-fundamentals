package org.zeroturnaround.jf2012.concurrency.cancel.lock;

public class Unlock3 {

  public static void main(String[] args) throws Exception {
    Object lock = new Object();
    
    Thread t1 = new KeepLock(lock);
    t1.setDaemon(true);
    t1.start();
    Thread.sleep(1000);

    Thread t2 = new KeepLock(lock);
    t2.start();
    Thread.sleep(1000);
    System.out.println("Interrupting...");
    t2.interrupt();
  }

}
