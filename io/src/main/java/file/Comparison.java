package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class Comparison {

  public static void path() {
    Path p = Paths.get("files");
    File f = new File("files");

    {
      Path c = p.resolve("foo.txt");
    }
    {
      File c = new File(f, "foo.txt");
    }

    {
      Path name = p.getFileName();
    }
    {
      String name = f.getName();
    }

    {
      Path parent = p.getParent();
    }
    {
      File pf = f.getParentFile();
      String pp = f.getParent();
    }

    {
      Path a = p.toAbsolutePath();
    }
    {
      File af = f.getAbsoluteFile();
      String ap = f.getAbsolutePath();
    }

    {
      boolean ia = p.isAbsolute();
    }
    {
      boolean ia = f.isAbsolute();
    }
  }

  public static void filesReadOnly() throws IOException {
    Path p = Paths.get("files");
    File f = new File("files");

    {
      boolean exists = Files.exists(p);
    }
    {
      boolean exists = f.exists();
    }

    {
      boolean isFile = Files.isRegularFile(p);
    }
    {
      boolean isFile = f.isFile();
    }

    {
      boolean isDir = Files.isDirectory(p);
    }
    {
      boolean isDir = f.isDirectory();
    }

    {
      FileTime lm = Files.getLastModifiedTime(p);
    }
    {
      long lm = f.lastModified();
    }

    {
      long size = Files.size(p);
    }
    {
      long size = f.length();
    }
  }

  public static void filesWriting() throws IOException {
    Path p = Paths.get("files");
    File f = new File("files");

    Files.createFile(p);
    {
      boolean ok = f.createNewFile();
    }

    Files.createDirectory(p);
    {
      boolean ok = f.mkdir();
    }

    Files.createDirectories(p);
    {
      boolean ok = f.mkdirs();
    }

    Files.delete(p);
    {
      boolean existed = Files.deleteIfExists(p);
    }
    {
      boolean ok = f.delete();
    }

    Files.move(p, p);
    {
      boolean ok = f.renameTo(f);
    }
  }

}
