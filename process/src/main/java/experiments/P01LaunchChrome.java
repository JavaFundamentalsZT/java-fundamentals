package experiments;


public class P01LaunchChrome {

  public static void main(String[] args) throws Exception {
    Thread.sleep(2000);
    ProcessBuilder builder = new ProcessBuilder(
        "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome",
      "https://courses.cs.ut.ee/2014/javaFund/fall");
    builder.start();
  }

}
