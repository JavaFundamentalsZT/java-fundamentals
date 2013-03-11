package processbuilder.output;
import java.lang.ProcessBuilder.Redirect;




/**
 * Java 7. User sees the output but the program cannot use it by itself.
 * 
 * @author Rein Raudjärv
 */
public class AllInheritJava7Example {

  public static void main(String[] args) throws Exception {
    // Read and output 3 lines
    ProcessBuilder builder = new ProcessBuilder("head", "-n", "3");
    builder.inheritIO();
//    builder.redirectOutput(Redirect.INHERIT);
    Process process = builder.start();
    process.waitFor();
  }

}
