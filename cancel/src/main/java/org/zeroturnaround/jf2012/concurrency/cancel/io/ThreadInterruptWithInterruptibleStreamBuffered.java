package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ThreadInterruptWithInterruptibleStreamBuffered extends Thread {

  private final InputStream in;

  public ThreadInterruptWithInterruptibleStreamBuffered(InputStream in) {
    this.in = in;
  }

  public static void main(String[] args) throws Exception {
    replaceStdIn();

    ThreadInterruptWithInterruptibleStreamBuffered thread = new ThreadInterruptWithInterruptibleStreamBuffered(System.in);
    try {
      thread.start();
      thread.join(5*1000);
    }
    finally {
      System.out.println("Interrupting...");
      thread.interrupt();
    }
  }

  private static void replaceStdIn() throws IOException {
    System.setIn(new BufferedInterruptibleInputStream(System.in));
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

}
