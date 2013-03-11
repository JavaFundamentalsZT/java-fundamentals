package copy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class CopyLines {

  public static void main(String[] args) throws IOException {
    BufferedReader in = null;
    PrintWriter out = null;
    try {
      in = new BufferedReader(new FileReader("input.txt"));
      out = new PrintWriter(new FileWriter("output.txt"));
      String line;
      while ((line = in.readLine()) != null)
        out.println(line);
    } finally {
      if (in != null) in.close();
      if (out != null) out.close();
    }
  }

}
