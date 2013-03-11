package processbuilder.output;
import java.lang.ProcessBuilder.Redirect;




/**
 * Java 7. User sees the output but the program cannot use it by itself.
 * 
 * @author Rein Raudjärv
 */
public class OutputInheritExample {

  public static void main(String[] args) throws Exception {
    ProcessBuilder builder = new ProcessBuilder("ps");
    builder.redirectOutput(Redirect.INHERIT);
    Process process = builder.start();
    process.waitFor();
  }

}
