package console;

import java.io.Console;
import java.io.IOException;

public class ReadPassword {

  public static void main(String[] args) throws IOException {
    Console console = System.console();
    if (console == null)
      throw new IllegalStateException("No console.");
    char[] password = console.readPassword("Enter password: ");
    if (new String(password).equals("123456"))
      console.format("Permission granted!%n");
    else
      console.format("Permission denied!%n");
  }

}
