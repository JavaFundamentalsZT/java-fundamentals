package file.nio2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

public class Delete {

  public static void main(String[] args) throws IOException {
    Path file = Paths.get("/Users/laura/delete-file");
    Files.delete(file); // May throw NoSuchFileException
    Files.deleteIfExists(file);

    Path root = Paths.get("/Users/madis/delete-dir");
    FileUtils.forceDelete(root.toFile()); // May throw FileNotFoundException
    if (Files.exists(root)) {
      FileUtils.forceDelete(root.toFile());
    }
  }

}
