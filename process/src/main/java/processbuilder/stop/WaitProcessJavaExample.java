package processbuilder.stop;





public class WaitProcessJavaExample {

  public static void main(String[] args) throws Exception {
    System.out.println("Started.");
    ProcessBuilder builder = new ProcessBuilder("/Applications/Calculator.app/Contents/MacOS/Calculator");
    Process process = builder.start();
    process.waitFor();
    System.out.println("Finished.");
  }

}
