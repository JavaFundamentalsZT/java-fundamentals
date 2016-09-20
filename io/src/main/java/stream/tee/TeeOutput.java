package stream.tee;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.io.output.TeeOutputStream;

public class TeeOutput {

  public static void main(String[] args) throws IOException {
    OutputStream out = new FileOutputStream("out.txt");
    System.setOut(new PrintStream(
        new TeeOutputStream(System.out, out)));

    System.out.print("Enter your name: ");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String name = reader.readLine();
    System.out.format("Hello, %s!%n", name);
  }

}
