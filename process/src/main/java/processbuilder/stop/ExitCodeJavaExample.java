package processbuilder.stop;





public class ExitCodeJavaExample {

  public static void main(String[] args) throws Exception {
    System.out.println("Started.");
    ProcessBuilder pb = new ProcessBuilder("cat", "out.txt");
    pb.inheritIO();
    Process process = pb.start();
    int exit = process.waitFor();
    if (exit != 0)
      throw new IllegalStateException("Exit code: " + exit);
    // 0 - success
    // 1 - file not found
    System.out.println("Exit: " + exit);
    System.out.println("Finished.");
  }

}
