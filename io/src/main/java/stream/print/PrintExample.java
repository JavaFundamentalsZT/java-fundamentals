package stream.print;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class PrintExample {

  public static void main(String[] args) throws IOException {
    String name = "Vello";
    byte[] bytes = null;
    {
      PrintStream out = System.out;
      out.println("Hello " + name);
      out.format("Hello %s%n", name);
      out.write(bytes);
    }

    try (PrintStream out = new PrintStream(new FileOutputStream("print.out"))) {
      out.println("Hello " + name);
      out.format("Hello %s%n", name);
      out.write(bytes);
    }

    try (PrintWriter out = new PrintWriter(new File("print.txt"), "UTF-8")) {
      out.println("Hello " + name);
      out.format("Hello %s%n", name);
//       out.write(bytes);
    }
  }

}
