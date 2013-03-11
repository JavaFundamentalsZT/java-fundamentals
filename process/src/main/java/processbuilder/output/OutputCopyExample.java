package processbuilder.output;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;




/**
 * Java 7. Output is read while the process is still running.
 * 
 * @author Rein Raudjärv
 */
public class OutputCopyExample {

  public static void main(String[] args) throws Exception {
    ProcessBuilder builder = new ProcessBuilder("ps");
    Process process = builder.start();

    InputStream out = process.getInputStream();
    IOUtils.copy(out, System.out);

    process.waitFor();
  }

}
