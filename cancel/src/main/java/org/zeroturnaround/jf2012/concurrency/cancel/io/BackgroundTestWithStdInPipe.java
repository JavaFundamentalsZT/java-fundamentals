package org.zeroturnaround.jf2012.concurrency.cancel.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class BackgroundTestWithStdInPipe {

  public static void main(String[] args) throws Exception {
    wrapStdInWithPipeInputStream();

    for (int i = 1; i <= 5; i++) {
      System.out.println(i);
      Thread.sleep(1000);
    }
//    String name = new BufferedReader(new InputStreamReader(System.in)).readLine();
//    System.out.printf("Hi, %s!%n", name);
    String name = new BufferedReader(new InputStreamReader(System.in)).readLine();
    System.out.printf("Hi, %s!%n", name);
  }
  
  private static void wrapStdInWithPipeInputStream() throws IOException {
    PipedOutputStream po = new PipedOutputStream();
    PipedInputStream pi = new PipedInputStream(po);
    Thread pumper = new Thread(new StreamPumper(System.in, po));
    pumper.setDaemon(true);
    pumper.start();
    System.setIn(pi);
  }
}
