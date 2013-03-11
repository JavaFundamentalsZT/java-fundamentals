package stop;

import util.StackTrace;




public class ProcessReaperThreadExample {

  public static void main(String[] args) throws Exception {
    Runtime.getRuntime().exec("/Applications/Calculator.app/Contents/MacOS/Calculator");
    
    // See process reaper thread
    System.out.println(StackTrace.toStringAll());
  }

}
