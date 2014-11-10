package copy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyBytesBuffered {
  public static void main(String[] args) throws Exception {
    FileInputStream in = null;
    FileOutputStream out = null;
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;

    try {
      in = new FileInputStream("input-large.data");
      bis = new BufferedInputStream(in);

      out = new FileOutputStream("output.data");
      bos = new BufferedOutputStream(out);

      int c;
      byte[] bytes = new byte[8192];
      while ((c = bis.read(bytes)) != -1) {
        bos.write(bytes, 0, c);
      }
    }
    finally {
      if (in != null)
        in.close();
      if (out != null)
        out.close();
      if (bis != null)
        bis.close();
      if (bos != null)
        bos.close();
    }
  }
}
