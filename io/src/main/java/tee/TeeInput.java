package tee;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.io.input.TeeInputStream;

public class TeeInput {

  public static void main(String[] args) throws IOException {
    OutputStream out = new FileOutputStream("in.txt");
    System.setIn(new TeeInputStream(System.in, out));

    System.out.print("Enter your name: ");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String name = reader.readLine();
    System.out.format("Hello, %s!%n", name);
  }

}
