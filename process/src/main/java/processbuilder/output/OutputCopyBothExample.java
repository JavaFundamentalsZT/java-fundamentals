package processbuilder.output;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;




/**
 * Java 7. Output is read while the process is still running.
 * 
 * @author Rein Raudjärv
 */
public class OutputCopyBothExample {

  public static void main(String[] args) throws Exception {
    ProcessBuilder pb = new ProcessBuilder("ls", "notfound");
    pb.redirectErrorStream(true);
    Process process = pb.start();

    InputStream out = process.getInputStream();
    IOUtils.copy(out, System.out);

    process.waitFor();
  }

}
