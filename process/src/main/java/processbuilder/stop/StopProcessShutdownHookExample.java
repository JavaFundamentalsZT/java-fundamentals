package processbuilder.stop;





public class StopProcessShutdownHookExample {

  public static void main(String[] args) throws Exception {
    System.out.println("Started.");
    
    ProcessBuilder builder = new ProcessBuilder("/Applications/Calculator.app/Contents/MacOS/Calculator");
    final Process process = builder.start();

    // Destroy the process on shutdown
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        System.out.println("Destroying...");
        process.destroy();
        System.out.println("Shutdown hook finished.");
      }
    });
    
    // Wait until it's closed
    process.waitFor();
    
    System.out.println("Finished.");
  }

}
