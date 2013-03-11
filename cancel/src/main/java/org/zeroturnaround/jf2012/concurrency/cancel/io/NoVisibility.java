package org.zeroturnaround.jf2012.concurrency.cancel.io;

class NoVisibility {
  static boolean ready;
  static int number;
  static class ReaderThread extends Thread {
    public void run() {
      while (!ready) Thread.yield();
      System.out.println(number);
    }
  }
  public static void main(String[] args) {
    new ReaderThread().start();
    number = 42; ready = true;
  }
}
