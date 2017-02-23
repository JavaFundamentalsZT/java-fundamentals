package archive;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaZipExmaple {

  private static final Logger log = LoggerFactory.getLogger(JavaZipExmaple.class);

  public static void main(String[] args) throws IOException {
    {
      Path src = Paths.get("/Users/rein/Downloads/VirtualBox-4.3.30-101610-OSX.dmg");
      Path dest = Paths.get(src + ".zip");
      packFile(src, dest);
    }
    {
      Path src = Paths.get("/Users/rein/tmp");
      Path dest = Paths.get("/Users/rein/tmp/test.zip");
      packDir(src, dest);
    }
    {
      Path src = Paths.get("/Users/rein/tmp/test.zip");
      Path dest = Paths.get("/Users/rein/tmp/extract");
      unpackAll(src, dest);
      unpackAllUsingZipFile(src, dest);
    }
  }

  private static void packFile(Path src, Path dest) throws IOException {
    log.info("Packing {} into {}", src, dest);
    log.info("Uncompressed size: {}", Files.size(src));
    try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(dest));
        ZipOutputStream zo = new ZipOutputStream(out)) {
      zo.putNextEntry(new ZipEntry(src.getFileName().toString()));
      Files.copy(src, zo);
      zo.closeEntry();
    }
    log.info("Compressed size: {}", Files.size(dest));
  }

  private static void packDir(Path src, Path dest) throws IOException {
    log.info("Packing {} into {}", src, dest);
    try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(dest));
        ZipOutputStream zo = new ZipOutputStream(out);
        Stream<Path> dirStream = Files.walk(src)) {
      dirStream.filter(p -> !p.equals(src)).forEach(path -> packEntry(src, zo, path));
    }
    log.info("Compressed size: {}", Files.size(dest));
  }

  private static void packEntry(Path src, ZipOutputStream zo, Path path) {
    try {
      log.trace("Packing path {}", path);
      doPackEntry(src, zo, path);
    }
    catch (IOException e) {
      throw new UncheckedIOException(path.toString(), e);
    }
  }

  private static void doPackEntry(Path src, ZipOutputStream zo, Path path) throws IOException {
    String name = src.relativize(path).toString().replace('\\', '/');
    boolean isDir = Files.isDirectory(path);
    if (isDir) {
      name += "/";
    }
    log.debug("Packing {}", name);
    ZipEntry e = new ZipEntry(name);
    zo.putNextEntry(e);
    if (!isDir) {
      Files.copy(path, zo);
    }
    zo.closeEntry();
  }

  private static void unpackAll(Path src, Path dest) throws IOException {
    log.info("Unpacking {} into {}", src, dest);
    log.info("Compressed size: {}", Files.size(src));
    try (ZipInputStream in = new ZipInputStream(new BufferedInputStream(Files.newInputStream(src)))) {
      ZipEntry entry;
      while ((entry = in.getNextEntry()) != null) {
        Path p = dest.resolve(entry.getName());
        Files.createDirectories(p.getParent());
        if (!entry.isDirectory()) {
          Files.copy(in, p, StandardCopyOption.REPLACE_EXISTING);
        }
      }
    }
  }

  private static void unpackAllUsingZipFile(Path src, Path dest) throws IOException {
    log.info("Unpacking {} into {}", src, dest);
    log.info("Compressed size: {}", Files.size(src));
    try (ZipFile zf = new ZipFile(src.toFile())) {
      Enumeration<? extends ZipEntry> en = zf.entries();
      while (en.hasMoreElements()) {
        ZipEntry entry = en.nextElement();
        Path p = dest.resolve(entry.getName());
        Files.createDirectories(p.getParent());
        if (!entry.isDirectory()) {
          InputStream in = zf.getInputStream(entry);
          Files.copy(in, p, StandardCopyOption.REPLACE_EXISTING);
        }
      }
    }
  }

}
