package archive;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.zeroturnaround.zip.ZipUtil;

public class ZtZipExample {

  public static void main(String[] args) {
    Path dir = Paths.get("/Users/rein/tmp/");
    Path archive = Paths.get("/Users/rein/tmp/test.zip");
    // Create an archive
    ZipUtil.pack(dir.toFile(), archive.toFile());
    // Extract an archive
    ZipUtil.unpack(archive.toFile(), dir.toFile());
    // Print all entry names
    ZipUtil.iterate(archive.toFile(), System.out::println);
  }

}
