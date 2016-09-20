package file.nio2;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirStream {

  public static void main(String[] args) throws IOException {
    Path dir = Paths.get(".");
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
      for (Path file: stream) {
        System.out.println(file.getFileName());
      }
    } catch (IOException | DirectoryIteratorException x) {
      // IOException can never be thrown by the iteration.
      // In this snippet, it can only be thrown by newDirectoryStream.
      System.err.println(x);
    }

    {
      DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
    }
    {
      DirectoryStream<Path> stream = Files.newDirectoryStream(dir, new Filter<Path>() {
        public boolean accept(Path path) throws IOException {
          return Files.isDirectory(path);
        }
      });
    }
    {
      DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.class");
    }
  }

}
