package copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyBytes {

  public static void main(String[] args) throws IOException {
    FileInputStream in = null;
    FileOutputStream out = null;
    try {
      in = new FileInputStream("input.data");
      out = new FileOutputStream("output.data");
      int c;
      while ((c = in.read()) != -1)
        out.write(c);
    }
    finally {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
    }
  }

}
