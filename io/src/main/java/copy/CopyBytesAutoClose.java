package copy;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class CopyBytesAutoClose {

  public static void main(String[] args) throws IOException {
    try (
        InputStream in = new FileInputStream("input.data");
        OutputStream out = new FileOutputStream("output.data");
        ) {
      int c;
      while ((c = in.read()) != -1)
        out.write(c);
    }
  }

}
