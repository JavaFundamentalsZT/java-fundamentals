package console;

import java.io.Console;
import java.io.IOException;

public class ReadConsole {

  public static void main(String[] args) throws IOException {
    Console console = System.console();
    if (console == null)
      throw new IllegalStateException("No console.");
    String name = console.readLine("Enter your name: ");
    if (name.compareToIgnoreCase("Tom") == 0)
      console.format("%s is cool!%n", name);
    else
      console.format("Hello, %s!%n", name);
  }

}
