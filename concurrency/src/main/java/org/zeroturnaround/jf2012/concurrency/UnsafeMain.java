package org.zeroturnaround.jf2012.concurrency;

public class UnsafeMain implements Runnable {

  private static final int NO_THREADS = 10;

  private static final int INCREMENTS_PER_THREAD = 10;

  public void run() {
    for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
      UnsafeCounter.increment();
    }
  }

  public static void main(String[] args) throws Exception {
    Runnable task = new UnsafeMain();

    // Create and start threads
    Thread[] ts = new Thread[NO_THREADS];
    for (int i = 0; i < ts.length; i++) {
      ts[i] = new Thread(task);
      ts[i].start();
    }
    // Wait until all threads have finished
    for (int i = 0; i < ts.length; i++) {
      ts[i].join();
    }

    System.out.format("%d x %d = %d%n", NO_THREADS, INCREMENTS_PER_THREAD, UnsafeCounter.get());
  }

}
