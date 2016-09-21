package file.nio2;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

public class FileChecks {

  public static void main(String[] args) throws Exception {
    dir();
    link();

    Path p = Paths.get("/usr/bin/java");
    long size = Files.size(p); // 54624
    FileTime time = Files.getLastModifiedTime(p); // 2015-07-14T13:41:40Z
    long millis = time.toMillis(); // 1436881300000
    System.out.println(size);
    System.out.println(time);
    System.out.println(millis);
  }

  private static void dir() {
    Path p = Paths.get("/Users/rein");
    boolean exists = Files.exists(p); // true
    boolean notExists = Files.notExists(p); // false
    boolean isDir = Files.isDirectory(p); // true
    boolean isFile = Files.isRegularFile(p); // false
    boolean a = false;
    System.out.println(p);
    System.out.println(exists);
    System.out.println(notExists);
    System.out.println(isDir);
    System.out.println(isFile);
  }

  private static void link() {
    Path p = Paths.get("/usr/bin/java");
    boolean isFile = Files.isRegularFile(p); // true
    boolean isFile2 = Files.isRegularFile(p, LinkOption.NOFOLLOW_LINKS); // false
    boolean isLink = Files.isSymbolicLink(p); // true
    System.out.println(p);
    System.out.println(isFile);
    System.out.println(isFile2);
    System.out.println(isLink);
  }

}
