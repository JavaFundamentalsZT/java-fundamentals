package org.zeroturnaround.jf2012.concurrency.promise;

import org.zeroturnaround.jf2012.concurrency.promise.F.Action;
import org.zeroturnaround.jf2012.concurrency.promise.F.Promise;
import org.zeroturnaround.jf2012.concurrency.promise.F.Tuple;

class AddIntegerAction implements Action<Promise<Tuple<Integer, Integer>>> {

  private final Promise<Integer> p;

  AddIntegerAction(Promise<Integer> p) {
    this.p = p;
  }

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

}