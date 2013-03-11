package processbuilder.stop;



public class TimeoutProcessJavaExample {

  public static void main(String[] args) throws Exception {
    System.out.println("Started.");
    ProcessBuilder builder = new ProcessBuilder("/Applications/Calculator.app/Contents/MacOS/Calculator");
    final Process process = builder.start();
    WatchdogThread watchdog = new WatchdogThread(process, 10*1000);
    watchdog.start();
    try {
      process.waitFor();
    }
    finally {
      watchdog.interrupt(); // Stop the thread if it's still running
      System.out.println("Finished.");
      System.out.println("Watchdog killed process: " + watchdog.killed);
    }
  }

  private static class WatchdogThread extends Thread {

    private final Process process;
    private final long timeout;
    private volatile boolean killed;
    
    public WatchdogThread(Process process, long timeout) {
      this.process = process;
      this.timeout = timeout;
    }
    
    @Override
    public void run() {
      try {
        Thread.sleep(timeout);
        killed = true;
        process.destroy();
      }
      catch (InterruptedException e) {
        // Stopping
      }
    }
    
  }
  
}
