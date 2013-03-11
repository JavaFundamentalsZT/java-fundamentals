package console;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Sum {

public static void main(String[] args) {
  sum(System.in, System.out);
}

static void sum(InputStream in, PrintStream out) {
  Scanner scanner = new Scanner(in);
  int a = scanner.nextInt();
  int b = scanner.nextInt();
  int s = a + b;
  out.print(s);
}

}
