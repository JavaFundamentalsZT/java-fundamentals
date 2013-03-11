package commonsexec.output;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;




/**
 * Java 1.3+. Output is pumped to this process' output.
 * 
 * @author Rein Raudjärv
 */
public class OutputCommonsExecInheritIOExample {

  public static void main(String[] args) throws Exception {
    DefaultExecutor executor = new DefaultExecutor();
    executor.execute(new CommandLine("ps"));
  }

}
