package processbuilder;

import java.io.File;
import java.util.Map;




public class EnvironmentProcessBuilderExample {

  public static void main(String[] args) throws Exception {
    ProcessBuilder builder = new ProcessBuilder("env");
    builder.inheritIO();
    Map<String, String> env = builder.environment();
    env.put("foo", "bar");
    env.put("PATH", "/Applications/Calculator.app/Contents/MacOS" + File.pathSeparator + env.get("PATH"));
    builder.start();

  
    // java.io.IOException: Cannot run program "Calculator": error=2, No such file or directory
//    ProcessBuilder builder = new ProcessBuilder("Calculator");
//    Map<String, String> env = builder.environment();
//    env.put("PATH", "/Applications/Calculator.app/Contents/MacOS" + File.pathSeparator + env.get("PATH"));
//    builder.start();

  }

}
