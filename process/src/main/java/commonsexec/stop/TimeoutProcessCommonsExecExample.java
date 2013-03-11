package commonsexec.stop;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;


public class TimeoutProcessCommonsExecExample {

  public static void main(String[] args) throws Exception {
    System.out.println("Started.");
    CommandLine cmdLine = new CommandLine("/Applications/Calculator.app/Contents/MacOS/Calculator");
    DefaultExecutor executor = new DefaultExecutor();
    ExecuteWatchdog watchDog = new ExecuteWatchdog(10*1000);
    executor.setWatchdog(watchDog);
    try {
      executor.execute(cmdLine);
    }
    finally {
      System.out.println("Finished.");
      System.out.println("Watchdog killed process: " + watchDog.killedProcess());
    }
  }

}
