package org.zeroturnaround.jf.debugging;

public class DebugLonger {
  private static long l = 1000l;
  private String name;

  public DebugLonger(String name) {
    this.name = name;
  }

  public static void main(String[] args) throws Exception {
    Thread.sleep(10000);
    DebugLonger debug = new DebugLonger("Hello World!");

    long sum = 0;
    for (int i = 0; i < 100; i++) {
      sum += i;
      debug.firstMethod(i);
    }
    System.out.println("Final sum: " + sum);
  }

  private boolean firstMethod(int sum) {
    System.out.println("Sum: " + sum);
    int i = sum / 4;
    int j = sum / 2;
    return secondMethod(i, j);
  }

  private boolean secondMethod(int first, int second) {
    for (int i = 0; i < first; i++) {
      for (int j = 0; j < second; j++) {
        if ((i + 1 + j) % 11 == 0) {
          return true;
        }
      }
    }
    return false;
  }
}
