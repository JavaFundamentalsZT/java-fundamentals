package processbuilder.input;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;




public class InputJava7Example {

  public static void main(String[] args) throws Exception {
    // Read and output 3 lines
    ProcessBuilder builder = new ProcessBuilder("head", "-n", "3");
    builder.redirectOutput(Redirect.INHERIT);
    Process process = builder.start();
    PrintStream in = new PrintStream(process.getOutputStream(), true);
    in.println("a");
    in.println("b");
    in.println("c");
    in.println("d");
    process.waitFor();
  }

}
