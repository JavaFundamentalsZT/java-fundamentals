package console;

import java.io.Console;
import java.io.IOException;

public class ReadConsole {

  public static void main(String[] args) throws IOException {
    Console console = System.console();
    if (console == null)
      throw new IllegalStateException("No console.");
    String name = console.readLine("Enter your name: ");
    console.format("Hello, %s!%n", name);
  }

}
