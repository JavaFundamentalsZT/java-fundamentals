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
      ProcessBuilder builder = new ProcessBuilder("java", "-cp", "target/classes", 
          OutputGeneratorMain.class.getName(), String.valueOf(1024 * 128));
      builder.redirectError(Redirect.INHERIT);
      process = builder.start();
      process.waitFor();
    }
    finally {
      if (process != null) {
        process.destroyForcibly();
      }
    }
  }

}
