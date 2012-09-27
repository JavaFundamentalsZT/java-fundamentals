package org.zeroturnaround.jf2012.concurrency.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {

  final int n;

  Fibonacci(int n) {
    this.n = n;
  }

  public Integer compute() {
    if (n <= 1)
      return n;
    Fibonacci f1 = new Fibonacci(n - 1);
    f1.fork();
    Fibonacci f2 = new Fibonacci(n - 2);
    return f2.compute() + f1.join();
  }

  public static void main(String[] args) {
    long start = System.currentTimeMillis();

    int n = 40;
    System.out.printf("fibonacci(%d) = ", n);

    ForkJoinPool pool = new ForkJoinPool();
    Integer f = pool.invoke(new Fibonacci(n));
    System.out.println(f);
    long time = System.currentTimeMillis() - start;
    System.out.printf("Time spent: %d ms%n", time);

    pool.shutdown();
  }

}