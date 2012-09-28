package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BlockingIO2 extends Thread {

  public static void main(String[] args) throws Exception {
    BlockingIO2 thread = new BlockingIO2();
    thread.start();
    thread.join(1000);
    System.out.println("Interrupting...");
    thread.interrupt();
  }

  @Override
  public void run() {
    try {
      System.out.print("What's your name: ");
      String name = new BufferedReader(new InputStreamReader(System.in)).readLine();
      System.out.printf("Hi, %s!%n", name);
      System.out.printf("I was interrupted: %b%n", Thread.currentThread().isInterrupted());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
