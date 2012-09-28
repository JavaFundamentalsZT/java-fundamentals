package org.zeroturnaround.jf2012.concurrency.promise;

import java.util.concurrent.Executor;

import org.zeroturnaround.jf2012.concurrency.promise.F.Action;
import org.zeroturnaround.jf2012.concurrency.promise.F.Promise;
import org.zeroturnaround.jf2012.concurrency.promise.F.Tuple;

public class PromisedFibonacci1 extends PromisedTask<Integer> {

  final Executor executor;

  final int n;

  PromisedFibonacci1(Executor executor, int n) {
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
    PromisedFibonacci1 f1 = newTask(n - 1);
    PromisedFibonacci1 f2 = newTask(n - 2);
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

  private PromisedFibonacci1 newTask(int n) {
    PromisedFibonacci1 result = new PromisedFibonacci1(executor, n);
    executor.execute(result);
    return result;
  }

}