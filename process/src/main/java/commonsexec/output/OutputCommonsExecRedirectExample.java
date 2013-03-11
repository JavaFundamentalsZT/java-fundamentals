package commonsexec.output;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;




/**
 * Java 1.3+. Output is pumped to a file which is read after the process has exited.
 * 
 * @author Rein Raudjärv
 */
public class OutputCommonsExecRedirectExample {

  public static void main(String[] args) throws Exception {
    File file = new File("out.txt");
    DefaultExecutor executor = new DefaultExecutor();
    FileOutputStream out = new FileOutputStream(file);
    try {
      executor.setStreamHandler(new PumpStreamHandler(out));
      executor.execute(new CommandLine("ps"));    
    }
    finally {
      out.close();
    }

    int i = 0;
    for (String line : FileUtils.readLines(file))
      System.out.println(i++ + ". " + line);
  }

}
