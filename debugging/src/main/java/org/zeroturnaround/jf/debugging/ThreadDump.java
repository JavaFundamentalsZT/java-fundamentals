package org.zeroturnaround.jf.debugging;

import java.math.BigInteger;
import java.util.Random;

public class ThreadDump {

  public static void main(String[] args) throws Exception {
    while (true) {
      ThreadDump dump = new ThreadDump();
      dump.spendSomeTime();
      Thread.sleep(5000);
    }
  }

  private void spendSomeTime() throws Exception {
    BigInteger veryBig = new BigInteger(1000, new Random());
    veryBig.nextProbablePrime();
  }

}
