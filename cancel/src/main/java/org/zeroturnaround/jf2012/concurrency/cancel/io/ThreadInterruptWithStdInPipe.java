package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class ThreadInterruptWithStdInPipe extends Thread {

  private final InputStream in;

  public ThreadInterruptWithStdInPipe(InputStream in) {
    this.in = in;
  }

  public static void main(String[] args) throws Exception {
    replaceStdIn();

    ThreadInterruptWithStdInPipe thread = new ThreadInterruptWithStdInPipe(System.in);
    try {
      thread.start();
      thread.join(1000);
    }
    finally {
      System.out.println("Interrupting...");
      thread.interrupt();
    }
  }

  private static void replaceStdIn() throws IOException {
    PipedOutputStream po = new PipedOutputStream();
    PipedInputStream pi = new PipedInputStream(po);
    Thread pumper = new Thread(new StreamPumper(System.in, po));
    pumper.setDaemon(true);
    pumper.start();
    System.setIn(pi);
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
