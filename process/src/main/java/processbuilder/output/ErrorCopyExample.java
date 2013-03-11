package processbuilder.output;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;




/**
 * Java 7. Output is read while the process is still running.
 * 
 * @author Rein Raudjärv
 */
public class ErrorCopyExample {

  public static void main(String[] args) throws Exception {
    ProcessBuilder builder = new ProcessBuilder("kill", "1000000");
    Process process = builder.start();

    InputStream err = process.getErrorStream();
    IOUtils.copy(err, System.err);

    process.waitFor();
  }

}
