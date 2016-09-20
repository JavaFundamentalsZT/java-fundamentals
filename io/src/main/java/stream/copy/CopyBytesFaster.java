package stream.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyBytesFaster {
  public static void main(String[] args) throws IOException {
    FileInputStream in = null;
    FileOutputStream out = null;
    try {
      in = new FileInputStream("input.data");
      out = new FileOutputStream("output.data");
      
      int c;
      byte[] bytes = new byte[8192];
      while ((c = in.read(bytes)) != -1) {
        out.write(bytes, 0, c);
      }
    }
    finally {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
    }
  }
}
