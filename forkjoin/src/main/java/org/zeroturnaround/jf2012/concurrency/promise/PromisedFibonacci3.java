package org.zeroturnaround.jf2012.concurrency.promise;

import java.util.concurrent.Executor;

import org.zeroturnaround.jf2012.concurrency.promise.F.Promise;

public class PromisedFibonacci3 extends PromisedTask<Integer> {

  final Executor executor;

  final int n;

  PromisedFibonacci3(Executor executor, int n) {
    this.executor = executor;
    this.n = n;
  }

  @Override
  public void run() {
    final Promise<Integer> p = getPromise();
    if (n <= 1) {
      p.invoke(n);
      return;
    }
    // Fork
    PromisedFibonacci3 f1 = newTask(n - 1);
    executor.execute(f1);
    // Invoke
    PromisedFibonacci3 f2 = newTask(n - 2);
    f2.run();
    // Join
    Promise.wait2(f1.getPromise(), f2.getPromise()).onRedeem(new AddIntegerAction(p));
  }

  private PromisedFibonacci3 newTask(int n) {
    return new PromisedFibonacci3(executor, n);
  }

}