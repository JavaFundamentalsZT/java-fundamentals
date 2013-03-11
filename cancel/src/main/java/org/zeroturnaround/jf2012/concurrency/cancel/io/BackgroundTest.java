package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BackgroundTest {

  public static void main(String[] args) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      for (int i = 1; i <= 5; i++) {
        System.out.println(i);
        Thread.sleep(1000);
      }
      System.out.print("What's your name: ");
      System.out.printf("Hi, %s!%n", in.readLine());
    }
  }
}
