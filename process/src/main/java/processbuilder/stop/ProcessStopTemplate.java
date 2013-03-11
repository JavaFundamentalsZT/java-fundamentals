package processbuilder.stop;





public class ProcessStopTemplate {

  public static void main(String[] args) throws Exception {
    ProcessBuilder builder = null;
    Process process = builder.start();
    try {
      // ...
    }
    finally {
      process.destroy();
    }
  }

}
