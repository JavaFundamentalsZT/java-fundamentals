package file.nio2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class PathsTest {

  public static void main(String[] args) throws IOException {
    {
      Path p = Paths.get("tmp/a/b");
      Files.createDirectories(p.getParent());
      Files.write(p, "c".getBytes(), StandardOpenOption.CREATE);
    }
    {
      Path p = Paths.get("tmp\\d\\e");
      Path parent = p.getParent();
      if (parent != null)
        Files.createDirectories(parent);
      Files.write(p, "f".getBytes(), StandardOpenOption.CREATE);
    }
  }

}
