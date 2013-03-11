package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceInvokeAll implements Callable<Void> {

  public static void main(String[] args) throws Exception {
    ExecutorService service = Executors.newSingleThreadExecutor();
    try {
      List<Callable<Void>> tasks = new ArrayList<Callable<Void>>();
      tasks.add(new ExecutorServiceInvokeAll());
      service.invokeAll(tasks, 1, TimeUnit.SECONDS);
    }
    finally {
      System.out.println("Shutting down...");
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
