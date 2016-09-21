package file.nio2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Copy {

  public static void main(String[] args) throws Exception {
    Path src = Paths.get("unicorn");
    Path dest = Paths.get("unicorn.copy");

    Files.copy(src, dest);

    byte[] data = Files.readAllBytes(src);
    Files.write(src, data);

    try (ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(dest)))) {
      out.putNextEntry(new ZipEntry("unicorn"));
      Files.copy(src, out);
      out.closeEntry();
    }

    // Verify it's a valid ZIP file
    ZipFile zf = new ZipFile(dest.toFile());
    zf.close();

    Path dir = Paths.get("output");
    try (ZipInputStream in = new ZipInputStream(new BufferedInputStream(Files.newInputStream(dest)))) {
      ZipEntry entry = in.getNextEntry();
      Files.copy(in, dir.resolve(entry.getName()));
      in.closeEntry();
    }
  }

}
