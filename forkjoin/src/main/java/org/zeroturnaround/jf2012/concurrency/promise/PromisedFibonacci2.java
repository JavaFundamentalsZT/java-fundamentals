package org.zeroturnaround.jf2012.concurrency.promise;

import java.util.concurrent.Executor;

import org.zeroturnaround.jf2012.concurrency.promise.F.Action;
import org.zeroturnaround.jf2012.concurrency.promise.F.Promise;
import org.zeroturnaround.jf2012.concurrency.promise.F.Tuple;

public class PromisedFibonacci2 extends PromisedTask<Integer> {

  final Executor executor;

  final int n;

  PromisedFibonacci2(Executor executor, int n) {
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
    PromisedFibonacci2 f1 = newTask(n - 1);
    executor.execute(f1);
    // Invoke
    PromisedFibonacci2 f2 = newTask(n - 2);
    f2.run();
    // Join
    Promise.wait2(f1.getPromise(), f2.getPromise()).onRedeem(new Action<Promise<Tuple<Integer,Integer>>>() {
      @Override
      public void invoke(Promise<Tuple<Integer, Integer>> result) {
        Tuple<Integer, Integer> t;
        try {
          t = result.get();
        }
        catch (Exception e) {
          throw new UnexpectedException(e);
        }
        p.invoke(t._1 + t._2);
      }
    });
  }

  private PromisedFibonacci2 newTask(int n) {
    return new PromisedFibonacci2(executor, n);
  }

}