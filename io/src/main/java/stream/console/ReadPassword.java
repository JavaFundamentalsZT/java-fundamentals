package stream.console;

import java.io.Console;
import java.io.IOException;
import java.util.Arrays;

public class ReadPassword {

  public static void main(String[] args) throws IOException {
    Console console = System.console();
    if (console == null)
      throw new IllegalStateException("No console.");
    char[] expected = {'1','2','3','4','5','6'};
    char[] actual = console.readPassword("Enter password: ");
    if (Arrays.equals(actual, expected))
      console.format("Permission granted!%n");
    else
      console.format("Permission denied!%n");
  }

}
