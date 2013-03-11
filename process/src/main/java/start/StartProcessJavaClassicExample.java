package start;





public class StartProcessJavaClassicExample {

  public static void main(String[] args) throws Exception {
    // java.io.IOException: Cannot run program "Calculator": error=2, No such file or directory
//    Runtime.getRuntime().exec("Calculator");
//    
//    // Success
//    Runtime.getRuntime().exec("/Applications/Calculator.app/Contents/MacOS/Calculator");
//    
//    // Success
//    Runtime.getRuntime().exec(new String[] { "/Applications/Calculator.app/Contents/MacOS/Calculator" });
//
//    
//    // java.io.IOException: Cannot run program "/Applications/App": error=2, No such file or directory
//    Runtime.getRuntime().exec("/Applications/App Store.app/Contents/MacOS/App Store");
//
//    // java.io.IOException: Cannot run program ""/Applications/App"
//    Runtime.getRuntime().exec("\"/Applications/App Store.app/Contents/MacOS/App Store\"");
//
//    // Success
//    Runtime.getRuntime().exec(new String[] { "/Applications/App Store.app/Contents/MacOS/App Store" });
    
    
    Runtime.getRuntime().exec("Calculator");
  }

}
