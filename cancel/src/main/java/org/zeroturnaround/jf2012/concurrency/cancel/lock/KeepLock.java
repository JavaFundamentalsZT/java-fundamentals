package org.zeroturnaround.jf2012.concurrency.cancel.lock;

class KeepLock extends Thread {
  final Object lock;
  KeepLock(Object lock) { this.lock = lock; }
  public void run() {
    try {
      synchronized (lock) {
        while (true) Thread.sleep(1000); 
      }
    } catch (InterruptedException e) { }
  }
}
