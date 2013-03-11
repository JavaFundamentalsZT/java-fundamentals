package org.zeroturnaround.jf2012.concurrency.cancel.io;


public class BackgroundTestWithInterruptibleStream {

  public static void main(String[] args) throws Exception {
    System.setIn(new BufferedInterruptibleInputStream(System.in));
    
    while (true) {
      for (int i = 1; i <= 5; i++) {
        System.out.println(i);
        Thread.sleep(1000);
      }
      System.out.print("Press any key: ");
      System.out.printf("You pressed: %s!%n", (char) System.in.read());
    }
  }
}
