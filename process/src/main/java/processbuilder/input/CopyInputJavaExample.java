package processbuilder.input;

import org.apache.commons.io.IOUtils;




public class CopyInputJavaExample {

  public static void main(String[] args) throws Exception {
    // Read and output 3 lines
    ProcessBuilder builder = new ProcessBuilder("head", "-n", "3");
    final Process process = builder.start();
//    Pump pump = new Pump(process.getInputStream(), System.out);
//    pump.start();
//    System.out.println("Started to pipe System.in.");
    Pump pump = new Pump(System.in, process.getOutputStream());
    pump.start();
    IOUtils.copy(process.getInputStream(), System.out);
    
//    new Pump(System.in, process.getOutputStream()).run();
//    IOUtils.copy(System.in, process.getOutputStream());
    process.waitFor();
    System.out.println("Process finisged.");
  }
  
}
