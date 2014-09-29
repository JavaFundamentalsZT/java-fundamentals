package outputbuffer;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;


public class OutputGeneratorMain {

  private static class OutputGenerator implements Runnable, Closeable {

    private final Thread t;
    private final int numberOfBytesToWrite;
    private final PrintStream output;
    private volatile int numberOfBytesWritten;

    public OutputGenerator(int numberOfBytesToWrite, PrintStream output) {
      this.numberOfBytesToWrite = numberOfBytesToWrite;
      this.output = output;
      this.t = new Thread(this, "generator");
      this.t.start();
    }

    @Override
    public void run() {
      for (int i = 0; i < numberOfBytesToWrite
            && !Thread.currentThread().isInterrupted(); i++) {
        String str = String.valueOf(i);
        output.print(str.charAt(str.length()-1));
        numberOfBytesWritten++;
      }
    }

    @Override
    public void close() throws IOException {
      t.interrupt();
    }

    public boolean isRunning() {
      return numberOfBytesWritten < numberOfBytesToWrite;
    }
  }

  public static void main(String[] args) throws Exception {
    OutputGenerator g = new OutputGenerator(args.length == 0 ? 1024 : Integer.parseInt(args[0]), System.out);
    while (g.isRunning()) {
      Thread.sleep(1000);
      System.err.println("\nnumber of bytes written: " + g.numberOfBytesWritten);
    }
    g.close();
  }

}
