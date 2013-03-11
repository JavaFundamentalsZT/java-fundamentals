package commonsexec.stop;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;


public class WaitProcessCommonsExecExample {

  public static void main(String[] args) throws Exception {
    System.out.println("Started.");
    CommandLine cmdLine = new CommandLine("/Applications/Calculator.app/Contents/MacOS/Calculator");
    DefaultExecutor executor = new DefaultExecutor();
    executor.execute(cmdLine);
    System.out.println("Finished.");
  }

}
