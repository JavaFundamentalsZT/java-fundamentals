package processbuilder;




public class StartProcessProcessBuilderExample {

  public static void main(String[] args) throws Exception {
//    ProcessBuilder builder = new ProcessBuilder("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome", "http://jf.0t.ee");
    
//    ProcessBuilder builder = new ProcessBuilder("C:\\Users\\Rein\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe", "http://jf.0t.ee");
    
    ProcessBuilder builder = new ProcessBuilder("calc");
    
    
//    ProcessBuilder builder = new ProcessBuilder("/Applications/App Store.app/Contents/MacOS/App Store");
    
    // java.io.IOException: Cannot run program ""/Applications/App Store.app/Contents/MacOS/App Store"": error=2, No such file or directory
//    ProcessBuilder builder = new ProcessBuilder("\"/Applications/App Store.app/Contents/MacOS/App Store\"");
    
//    ProcessBuilder builder = new ProcessBuilder(new String[] { "/Applications/App Store.app/Contents/MacOS/App Store" });
    
//    List<String> commands = new ArrayList<>();
//    commands.add("/Applications/App Store.app/Contents/MacOS/App Store");
//    ProcessBuilder builder = new ProcessBuilder(commands);

    
//    ProcessBuilder builder = new ProcessBuilder();
//    builder.command().add("/Applications/App Store.app/Contents/MacOS/App Store");
    
//    ProcessBuilder builder = new ProcessBuilder();
//    builder.command("/Applications/App Store.app/Contents/MacOS/App Store");
    
//    ProcessBuilder builder = new ProcessBuilder();
//    builder.command(new String[] { "/Applications/App Store.app/Contents/MacOS/App Store" });

//    ProcessBuilder builder = new ProcessBuilder();
//    List<String> commands = new ArrayList<>();
//    commands.add("/Applications/App Store.app/Contents/MacOS/App Store");
//    builder.command(commands);
    
    
    builder.start();
  }

}
