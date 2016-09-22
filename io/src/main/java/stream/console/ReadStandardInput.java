package stream.console;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;

public class ReadStandardInput {

  public static void main(String[] args) throws IOException {
    System.out.print("Enter your name: ");

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Enter your name: ");
    String name = in.readLine();
    System.out.format("Hello, %s!%n", name);

    Console c = System.console();
    Reader a = System.console().reader();
    PrintWriter out = System.console().writer();
  }

}
