package experiments;

import java.lang.ProcessBuilder.Redirect;


public class P02LaunchPs {

  public static void main(String[] args) throws Exception {
    Thread.sleep(2000);
    ProcessBuilder builder = new ProcessBuilder("ps");
    builder.redirectOutput(Redirect.INHERIT);
    builder.start();
    //where is the output?
  }

}
