package org.zeroturnaround.jf2012.concurrency.forkjoin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ExecutedFibonacci implements Callable<Integer> {

  final ExecutorService executor;

  final int n;

  ExecutedFibonacci(ExecutorService executor, int n) {
    this.executor = executor;
    this.n = n;
  }

  @Override
  public Integer call() throws Exception {
    if (n <= 1)
      return n;
    ExecutedFibonacci f1 = newTask(n - 1);
    Future<Integer> future = executor.submit(f1);
    ExecutedFibonacci f2 = newTask(n - 2);
    return f2.call() + future.get();
  }

  private ExecutedFibonacci newTask(int n) {
    return new ExecutedFibonacci(executor, n);
  }
  
}