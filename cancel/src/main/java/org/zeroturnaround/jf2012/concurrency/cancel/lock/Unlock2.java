package org.zeroturnaround.jf2012.concurrency.cancel.lock;

public class Unlock2 {

  public static void main(String[] args) throws Exception {
Object lock = new Object();
Thread t1 = new KeepLock(lock);
Thread t2 = new KeepLock(lock);
t1.start();
Thread.sleep(1000);
t2.start();
System.out.println("Interrupting...");
t1.interrupt();
t2.interrupt();
  }

}
