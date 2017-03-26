package file.nio2;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

/**
 * Created by rein on 20/09/16.
 */
public class Tree {

  public static void main(String[] args) throws IOException {
    Path root = Paths.get("/Users/rein/tmp");

    try (Stream<Path> s = Files.walk(root)) {
      s.forEach(System.out::println);
    }

    try (Stream<Path> s = Files.walk(root)) {
      s.filter(Files::isDirectory).forEach(System.out::println);
    }

    Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("Dir: " + dir);
        return FileVisitResult.CONTINUE;
      }
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("File: " + file);
        return FileVisitResult.CONTINUE;
      }
    });
  }

}
