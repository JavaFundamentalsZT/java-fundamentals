package commonsexec.output;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;




/**
 * Java 1.3+. Output is read while the process is still running.
 * 
 * @author Rein Raudjärv
 */
public class OutputCommonsExecLogExample {

  public static void main(String[] args) throws Exception {
    DefaultExecutor executor = new DefaultExecutor();
    executor.setStreamHandler(new PumpStreamHandler(new LogOutputStream() {
      private int i;
      @Override
      protected void processLine(String line, int level) {
        System.out.println(i++ + ". " + line);
      }
    }));
    executor.execute(new CommandLine("ps"));    
  }

}
