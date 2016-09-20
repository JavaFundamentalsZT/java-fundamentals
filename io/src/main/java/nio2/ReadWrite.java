package nio2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

public class ReadWrite {

  private final Path src;
  private final Path dest;

  private final Multimap<String,Long> times = MultimapBuilder.linkedHashKeys().arrayListValues().build();

  ReadWrite(Path src, Path dest) {
    this.src = src;
    this.dest = dest;
  }

  public static void main(String[] args) throws IOException {
    Path src = Paths.get("/Users/rein/Downloads/android-studio-ide-145.2878421-mac.zip");
//    Path src = PathsFun.get("/Users/rein/Downloads/visualvm_138.zip");
//    Path src = PathsFun.get("/Users/rein/Downloads/VirtualBox.pkg");

    Path dest = Files.createTempFile("io-copy", ".data");
    System.out.println("Copying " + src + " to " + dest);
    ReadWrite runner = new ReadWrite(src, dest);
    for (int i = 0; i < 20; i++) {
      // Warm up
      if (i == 10) {
        runner.times.clear();
      }
      runner.runTests();
    }
    System.out.println();
    for (Map.Entry<String, Collection<Long>> entry: runner.times.asMap().entrySet()) {
      System.out.println(entry.getKey());
      Collection<Long> times = entry.getValue();
      System.out.println(times);
      System.out.println(times.stream().collect(Collectors.summarizingLong(x -> x)));
    }
    System.out.println("OK");
  }

  private void runTests() throws IOException {
    runTest("copyWholeFile", ReadWrite::copyWholeFile);
    runTest("readAndWriteWholeFile", ReadWrite::readAndWriteWholeFile);
//    runTest("readAndWriteByByte", ReadWrite::readAndWriteByByte);
    runTest("readAndWriteByByteUsingBufferedStreams", ReadWrite::readAndWriteByByteUsingBufferedStreams);
    runTest("readAndWriteByBuffer", ReadWrite::readAndWriteByBuffer);
    runTest("readAndWriteByBufferUsingBufferedStreams", ReadWrite::readAndWriteByBufferUsingBufferedStreams);
//    runTest("readAndWriteByLineUsingBufferedStreams", ReadWrite::readAndWriteByLineUsingBufferedStreams);
  }

  private void runTest(String name, Copier copier) throws IOException {
    System.out.println(name);
    Stopwatch sw = Stopwatch.createStarted();
    copier.copy(src, dest);
    long millis = sw.elapsed(TimeUnit.MILLISECONDS);
    System.out.println("Finished in " + millis + " ms");
    times.put(name, millis);
  }

  private interface Copier {
    void copy(Path src, Path dest) throws IOException;
  }

  private static void copyWholeFile(Path src, Path dest) throws IOException {
    Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
  }

  private static void readAndWriteWholeFile(Path src, Path dest) throws IOException {
    byte[] data = Files.readAllBytes(src);
    Files.write(src, data);
  }

  private static void readAndWriteByByte(Path src, Path dest) throws IOException {
    try (InputStream in = Files.newInputStream(src);
        OutputStream out = Files.newOutputStream(dest)) {
      int b;
      while ((b = in.read()) != -1) {
        out.write(b);
      }
    }
  }

  private static void readAndWriteByByteUsingBufferedStreams(Path src, Path dest) throws IOException {
    try (InputStream in = new BufferedInputStream(Files.newInputStream(src));
        OutputStream out = new BufferedOutputStream(Files.newOutputStream(dest))) {
      int b;
      while ((b = in.read()) != -1) {
        out.write(b);
      }
    }
  }

  private static void readAndWriteByBuffer(Path src, Path dest) throws IOException {
    try (InputStream in = Files.newInputStream(src);
        OutputStream out = Files.newOutputStream(dest)) {
      byte[] buffer = new byte[4096];
      int length;
      while ((length = in.read(buffer)) != -1) {
        out.write(buffer, 0, length);
      }
    }
  }

  private static void readAndWriteByBufferUsingBufferedStreams(Path src, Path dest) throws IOException {
    try (InputStream in = new BufferedInputStream(Files.newInputStream(src));
        OutputStream out = new BufferedOutputStream(Files.newOutputStream(dest))) {
      byte[] buffer = new byte[4096];
      int length;
      while ((length = in.read(buffer)) != -1) {
        out.write(buffer, 0, length);
      }
    }
  }

  private static void readAndWriteAllLines(Path src, Path dest) throws IOException {
    List<String> lines = Files.readAllLines(src);
    Files.write(dest, lines);
  }

  private static void readAndWriteAllLinesUTF8(Path src, Path dest) throws IOException {
    Charset cs = StandardCharsets.UTF_8;
    List<String> lines = Files.readAllLines(src, cs);
    Files.write(dest, lines, cs);
  }

  private static void readAndWriteByLineUsingBufferedStreams(Path src, Path dest) throws IOException {
    try (BufferedReader in = Files.newBufferedReader(src);
        BufferedWriter out = Files.newBufferedWriter(dest)) {
      String line;
      while ((line = in.readLine()) != null) {
        out.write(line.toUpperCase(Locale.ENGLISH));
      }
    }
  }

  private static void readAndWriteByLineUsingBufferedStreamsUTF8(Path src, Path dest) throws IOException {
    Charset cs = StandardCharsets.UTF_8;
    try (BufferedReader in = Files.newBufferedReader(src, cs);
        BufferedWriter out = Files.newBufferedWriter(dest, cs)) {
      String line;
      while ((line = in.readLine()) != null) {
        out.write(line.toUpperCase(Locale.ENGLISH));
      }
    }
  }

}
