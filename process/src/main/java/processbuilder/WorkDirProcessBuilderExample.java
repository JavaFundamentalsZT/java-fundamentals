package processbuilder;
import java.io.File;
import java.lang.ProcessBuilder.Redirect;




public class WorkDirProcessBuilderExample {

  public static void main(String[] args) throws Exception {
    ProcessBuilder builder = new ProcessBuilder("ls");
    builder.directory(new File("/"));
    builder.redirectOutput(Redirect.INHERIT);
    Process process = builder.start();
    process.waitFor();
  }

}
