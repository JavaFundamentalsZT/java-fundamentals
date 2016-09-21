package file.nio2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

public class Create {

  public static void main(String[] args) throws Exception {
    createOrFail();
    createOrReplace();
    createTemp();
  }

  public static void createOrFail() throws IOException {
    Path file = Paths.get("/Users/anu/jf/newDir/new.txt");

    Files.createDirectories(file.getParent());

//    Files.createDirectory(file.getParent());
    // May throw NoSuchFileException or FileAlreadyExistsException

    Files.createFile(file);
    // May throw NoSuchFileException or FileAlreadyExistsException
  }

  public static void createOrReplace() throws IOException {
    Path file = Paths.get("/Users/anu/jf/newDir/new.txt");

    Path parent = file.getParent();
    if (Files.exists(parent)) {
      FileUtils.cleanDirectory(parent.toFile());
    }
    else {
      Files.createDirectories(parent);
    }

    Files.createFile(file); // Works OK
  }

  public static void createTemp() throws IOException {
    {
      Path file = Files.createTempFile("homework", ".temp");
      Path dir = Files.createTempDirectory("homework");
      System.out.println(file);
      System.out.println(dir);
    }
    Path parent = Paths.get("/Users/toomas/tmp");
    Path file = Files.createTempFile(parent, "homework", ".temp");
    Path dir = Files.createTempDirectory(parent, "homework");
  }

}
