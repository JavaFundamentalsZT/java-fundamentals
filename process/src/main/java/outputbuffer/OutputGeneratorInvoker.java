package outputbuffer;
import java.lang.ProcessBuilder.Redirect;

/**
 * Testing the buffer size of standard output of the operating system.
 * 
 * @see OutputGeneratorMain
 */
public class OutputGeneratorInvoker {

  public static void main(String[] args) throws Exception {
    Process process = null;
    try {
      ProcessBuilder builder = new ProcessBuilder(
          "java", "-cp", "target/classes", 
          OutputGeneratorMain.class.getName(), String.valueOf(1024 * 128));
      builder.redirectError(Redirect.INHERIT);
      //builder.redirectOutput(Redirect.INHERIT);
      //builder.redirectError(new File("out.err"));
      process = builder.start();
      process.getOutputStream().close();
      process.waitFor();
    }
    finally {
      if (process != null) {
        process.destroyForcibly();
      }
    }
  }

}
