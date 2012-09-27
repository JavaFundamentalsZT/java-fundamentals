package org.zeroturnaround.jf2012.concurrency.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class IncrementTask extends RecursiveAction {

  private static final long serialVersionUID = 1L;

  static final int THRESHOLD = 2; 

  final long[] array;
  final int lo;
  final int hi;

  IncrementTask(long[] array, int lo, int hi) {
    this.array = array; this.lo = lo; this.hi = hi;
  }

  protected void compute() {
    if (hi - lo < THRESHOLD) {
      for (int i = lo; i < hi; ++i)
        array[i]++;
    }
    else {
      int mid = (lo + hi) >>> 1;
    invokeAll(new IncrementTask(array, lo, mid), new IncrementTask(array, mid, hi));
    }
  }

  public static void main(String[] args) {
    long[] a = {1, 323, 123, 23, 2, 1, 423, 5, 1221};
    System.out.println(Arrays.toString(a));

    ForkJoinPool pool = new ForkJoinPool();
    pool.invoke(new IncrementTask(a, 0, a.length));
    System.out.println(Arrays.toString(a));

    pool.shutdown();
  }

}