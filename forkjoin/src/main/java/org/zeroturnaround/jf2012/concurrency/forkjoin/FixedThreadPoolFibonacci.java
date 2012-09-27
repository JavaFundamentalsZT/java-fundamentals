package org.zeroturnaround.jf2012.concurrency.forkjoin;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class FixedThreadPoolFibonacci {

  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();

    int n = 20;
    if (args.length > 0)
      n = Integer.parseInt(args[0]);
    System.out.printf("fibonacci(%d) = ", n);

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    Integer f = new ExecutedFibonacci(executor, n).call();
    System.out.println(f);
    long time = System.currentTimeMillis() - start;
    System.out.printf("Time spent: %d ms%n", time);
    System.out.printf("Thread pool size: %d%n", executor.getPoolSize());

    executor.shutdown();
  }

}