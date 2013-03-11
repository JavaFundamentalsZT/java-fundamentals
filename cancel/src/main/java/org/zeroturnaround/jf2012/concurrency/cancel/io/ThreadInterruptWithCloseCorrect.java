package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ThreadInterruptWithCloseCorrect extends Thread {

  private final InputStream in;

  public ThreadInterruptWithCloseCorrect(InputStream in) {
    this.in = in;
  }

  public static void main(String[] args) throws Exception {
    ThreadInterruptWithCloseCorrect thread = new ThreadInterruptWithCloseCorrect(System.in);
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
      String name = new BufferedReader(new InputStreamReader(in)).readLine();
      System.out.printf("Hi, %s!%n", name);
      System.out.printf("I was interrupted: %b%n", isInterrupted());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void interrupt() {
    super.interrupt();
    System.out.println("Closing the input stream...");
    try {
      in.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}
