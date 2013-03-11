package commonsexec.output;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;




/**
 * Java 7. User sees the output but the program cannot use it by itself.
 * 
 * @author Rein Raudjärv
 */
public class AllInheritCommonsExecExample {

  public static void main(String[] args) throws Exception {
    // Read and output 3 lines
    DefaultExecutor executor = new DefaultExecutor();
    // By default System.in wouldn't be pumped
    executor.setStreamHandler(new PumpStreamHandler(System.out, System.err, System.in));
    CommandLine cmdLine = CommandLine.parse("head -n 3");
    executor.execute(cmdLine);
  }

}
