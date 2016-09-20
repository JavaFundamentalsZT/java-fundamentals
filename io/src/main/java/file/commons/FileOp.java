package file.commons;

import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileOp {

  public static void main(String[] args) throws IOException {
    FileUtils.getFile("example", "data");
  }

}
