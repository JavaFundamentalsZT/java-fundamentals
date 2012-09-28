package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BlockingIO1 implements Callable<Void> {

  public static void main(String[] args) throws Exception {
    ExecutorService service = Executors.newSingleThreadExecutor();
    Future<Void> future = null;
    try {
      future = service.submit(new BlockingIO1());
      future.get(1, TimeUnit.SECONDS);
    }
    catch (TimeoutException e) {
      System.out.println("Cancelling...");
      future.cancel(true);
    }
    finally {
      service.shutdown();
    }
  }

  public Void call() throws Exception {
    System.out.print("What's your name: ");
    String name = new BufferedReader(new InputStreamReader(System.in)).readLine();
    System.out.printf("Hi, %s!%n", name);
    System.out.printf("I was interrupted: %b%n", Thread.currentThread().isInterrupted());
    return null;
  }

}
