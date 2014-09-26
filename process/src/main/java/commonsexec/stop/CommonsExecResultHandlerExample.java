package commonsexec.stop;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;


public class CommonsExecResultHandlerExample {

  public static void main(String[] args) throws Exception {
    System.out.println("Started.");
    CommandLine cmdLine = new CommandLine("/Applications/Calculator.app/Contents/MacOS/Calculator");
    DefaultExecutor executor = new DefaultExecutor();

    executor.execute(cmdLine, new ExecuteResultHandler() {
      @Override
      public void onProcessFailed(ExecuteException e) {
        System.out.println("Process failed:");
        e.printStackTrace();
      }
      @Override
      public void onProcessComplete(int exitValue) {
        System.out.println("Process complete: " + exitValue);
      }
    });
  }

}
