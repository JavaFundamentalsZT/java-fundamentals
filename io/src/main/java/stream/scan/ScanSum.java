package stream.scan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ScanSum {
  public static void main(String[] args) throws IOException {
    Scanner s = null;
    double sum = 0;
    try {
      s = new Scanner(new BufferedReader(new FileReader("numbers.txt")));
      while (s.hasNext()) {
        if (s.hasNextDouble())
          sum += s.nextDouble();
        else
          s.next();
      }
    } finally {
      if (s != null) s.close();
    }
    System.out.println(sum);
  }
}
