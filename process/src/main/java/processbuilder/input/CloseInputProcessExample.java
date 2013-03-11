package processbuilder.input;

import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;





public class CloseInputProcessExample {

  public static void main(String[] args) throws Exception {
    System.out.println("Started.");
    ProcessBuilder builder = new ProcessBuilder("tr", "-u", "[:lower:]", "[:upper:]");
    builder.redirectOutput(Redirect.INHERIT);
    Process process = builder.start();
    PrintWriter in = new PrintWriter(process.getOutputStream(), true);
    in.println("James");
    in.println("Bond");
    in.close();
    process.waitFor();
    System.out.println("Finished.");
  }

}
