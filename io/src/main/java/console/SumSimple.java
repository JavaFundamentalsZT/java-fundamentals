package console;

import java.util.Scanner;

public class SumSimple {

  public static void main(String[] args) {
    try (
        Scanner scanner = new Scanner(System.in);) {
      int a = scanner.nextInt();
      int b = scanner.nextInt();
      int s = a + b;
      System.out.print(s);
    }
  }

}
