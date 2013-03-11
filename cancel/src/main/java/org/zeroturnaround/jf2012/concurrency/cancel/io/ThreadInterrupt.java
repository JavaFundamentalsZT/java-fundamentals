package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ThreadInterrupt extends Thread {

  public static void main(String[] args) throws Exception {
    ThreadInterrupt thread = new ThreadInterrupt();
    try {
      thread.start();
      thread.join(1000);
    }
    finally {
      System.out.println("Interrupting...");
      thread.interrupt();
    }
  }

  @Override
  public void run() {
    try {
      System.out.print("What's your name: ");
      String name = new BufferedReader(new InputStreamReader(System.in)).readLine();
      System.out.printf("Hi, %s!%n", name);
      System.out.printf("I was interrupted: %b%n", isInterrupted());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
