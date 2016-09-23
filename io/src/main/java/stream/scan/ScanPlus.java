package stream.scan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ScanPlus {

  public static void main(String[] args) throws IOException {
    readFromStdin();
  }

  public static void readFromStdin() throws IOException {
    Scanner scanner = new Scanner(System.in);
    int a = scanner.nextInt();
    int b = scanner.nextInt();
    int s = a + b;
    System.out.println(s);
  }

  public static void readFromFile() throws IOException {
    try (Scanner scanner = new Scanner(Files.newBufferedReader(Paths.get("numbers.txt")))) {
      int a = scanner.nextInt();
      int b = scanner.nextInt();
      int s = a + b;
      System.out.println(s);
    }
  }

}
