package file;

import java.io.File;
import java.io.IOException;

public class FileOp {

  public static void main(String[] args) throws IOException {
    File file = null;
    if (!file.delete())
      throw new IOException("Could not delete " + file);
    
  }

}
