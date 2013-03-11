package console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadStandardInput {

  public static void main(String[] args) throws IOException {
    System.out.print("Enter your name: ");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    String name = in.readLine();
    System.out.format("Hello, %s!%n", name);
  }

}
