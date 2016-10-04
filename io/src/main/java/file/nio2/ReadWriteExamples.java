package file.nio2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class ReadWriteExamples {

  public static void main(String[] args) throws IOException {
    binary();
    text();
    textJava8();
    readJava8Streams();
  }

  public static void binary() throws IOException {
    Path file = Paths.get("dog.data");

    byte[] data = Files.readAllBytes(file);
    Files.write(file, data);
  }

  public static void text() throws IOException {
    Path file = Paths.get("dog.txt");

    List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
    Files.write(file, lines, StandardCharsets.UTF_8);
  }

  public static void textJava8() throws IOException {
    Path file = Paths.get("dog.txt");

    List<String> lines = Files.readAllLines(file);
    Files.write(file, lines);
  }

  public static void readJava8Streams() throws IOException {
    Path file = Paths.get("dog.txt");

    try (Stream<String> s = Files.lines(file, StandardCharsets.UTF_8)) {
      s.forEach(line -> System.out.println(line));
    }
    try (Stream<String> s = Files.lines(file)) {
      s.forEach(line -> System.out.println(line));
    }
  }

}
