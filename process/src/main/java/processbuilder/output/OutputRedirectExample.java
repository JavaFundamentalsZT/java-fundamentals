package processbuilder.output;
import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;




/**
 * Java 7. Output is redirected to a file which is read after the process has exited.
 * 
 * @author Rein Raudjärv
 */
public class OutputRedirectExample {

  public static void main(String[] args) throws Exception {
    ProcessBuilder pb = new ProcessBuilder("ps");
    Path file = Paths.get("out.txt");
    pb.redirectOutput(Redirect.to(file.toFile()));
    Process process = pb.start();
    process.waitFor();
    // Process has finished writing the file
    try (Stream<String> lines = Files.lines(file)) {
      lines.forEach(System.out::println);
    }

//    int i = 0;
//    for (String line : FileUtils.readLines(file))
//      System.out.println(i++ + ". " + line);
  }

}
