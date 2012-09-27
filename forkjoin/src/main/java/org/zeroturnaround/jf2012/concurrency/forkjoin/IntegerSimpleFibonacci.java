package org.zeroturnaround.jf2012.concurrency.forkjoin;


public class IntegerSimpleFibonacci {

  public static Integer compute(Integer n) {
    if (n <= 1)
      return n;
    return compute(n - 1) + compute(n - 2);
  }
  
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    int n = 40;
    System.out.printf("fibonacci(%d) = ", n);
    int f = compute(n);
    System.out.println(f);
    long time = System.currentTimeMillis() - start;
    System.out.printf("Time spent: %d ms%n", time);
  }
  
}
