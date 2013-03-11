package processbuilder.output;
import java.io.File;
import java.lang.ProcessBuilder.Redirect;

import org.apache.commons.io.FileUtils;




/**
 * Java 7. Output is redirected to a file which is read after the process has exited.
 * 
 * @author Rein Raudjärv
 */
public class OutputRedirectExample {

  public static void main(String[] args) throws Exception {
    ProcessBuilder builder = new ProcessBuilder("ps");
    File file = new File("out.txt");
    builder.redirectOutput(Redirect.to(file));
    Process process = builder.start();
    process.waitFor();

    int i = 0;
    for (String line : FileUtils.readLines(file))
      System.out.println(i++ + ". " + line);
  }

}
