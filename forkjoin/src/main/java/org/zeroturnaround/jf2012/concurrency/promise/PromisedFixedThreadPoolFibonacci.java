package org.zeroturnaround.jf2012.concurrency.promise;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class PromisedFixedThreadPoolFibonacci {

  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();

    int n = 20;
    if (args.length > 0)
      n = Integer.parseInt(args[0]);
    System.out.printf("fibonacci(%d) = ", n);

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    PromisedTask<Integer> p = new PromisedFibonacci2(executor, n);
    p.run();
    Integer f = p.getPromise().get();
    System.out.println(f);
    long time = System.currentTimeMillis() - start;
    System.out.printf("Time spent: %d ms%n", time);
    System.out.printf("Thread pool size: %d%n", executor.getPoolSize());

    executor.shutdown();
  }

}