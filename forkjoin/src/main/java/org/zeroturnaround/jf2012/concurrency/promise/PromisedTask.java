package org.zeroturnaround.jf2012.concurrency.promise;

import org.zeroturnaround.jf2012.concurrency.promise.F.Promise;

public abstract class PromisedTask<V> implements Runnable {

  /**
   * Asynchronous result of this task.
   */
  private final Promise<V> promise = new Promise<>();

  /**
   * @return asynchronous result of this task.
   */
  public Promise<V> getPromise() {
    return promise;
  }
  
}
