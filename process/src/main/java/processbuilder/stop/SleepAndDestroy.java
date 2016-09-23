package processbuilder.stop;

import java.io.IOException;

public class SleepAndDestroy {

  public static void main(String[] args) throws InterruptedException, IOException {
    ProcessBuilder builder = new ProcessBuilder("ping", "ut.ee");
    builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
    Process process = builder.start();
    try {
      Thread.sleep(1000);
    }
    finally {
      process.destroy();
    }
  }

}
