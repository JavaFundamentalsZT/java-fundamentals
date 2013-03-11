package commonsexec;
import java.io.File;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;


public class StartProcessCommonsExecExample {

  public static void main(String[] args) throws Exception {
    CommandLine cmdLine = new CommandLine("/Applications/Calculator.app/Contents/MacOS/Calculator");
    
//    CommandLine cmdLine = new CommandLine(new File("/Applications/Calculator.app/Contents/MacOS/Calculator"));

    
//    CommandLine cmdLine = new CommandLine("/Applications/App Store.app/Contents/MacOS/App Store");
    
//    CommandLine cmdLine = new CommandLine(new File("/Applications/App Store.app/Contents/MacOS/App Store"));
    
    
    // java.io.IOException: Cannot run program "/Applications/App" (in directory "."): error=2, No such file or directory
//    CommandLine cmdLine = CommandLine.parse("/Applications/App Store.app/Contents/MacOS/App Store");
    
//    CommandLine cmdLine = CommandLine.parse("\"/Applications/App Store.app/Contents/MacOS/App Store\"");

    
    DefaultExecutor executor = new DefaultExecutor();
    executor.execute(cmdLine);
  }

}
