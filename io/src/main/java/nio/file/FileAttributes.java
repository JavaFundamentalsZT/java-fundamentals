package nio.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;
import java.util.Set;

public class FileAttributes {

  public static void test(Path path) throws IOException {
    long size = Files.size(path);
    boolean isDir = Files.isDirectory(path);
    boolean isDirNotLink = Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS);
    boolean isFile = Files.isRegularFile(path);
    boolean isFileNotLink = Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS);
    boolean isLink = Files.isSymbolicLink(path);
    boolean isHidden = Files.isHidden(path);
  }

  public static void getTime(Path path) throws IOException {
    FileTime time = Files.getLastModifiedTime(path);
    long millis = time.toMillis();
  }

  public static void setTime(Path path) throws IOException {
    long millis = System.currentTimeMillis();
    FileTime time = FileTime.fromMillis(millis);
    Files.setLastModifiedTime(path, time);
  }

  public static void getOwner(Path path) throws IOException {
    UserPrincipal owner = Files.getOwner(path);
    String name = owner.getName();
  }

  public static void setOwner(Path path) throws IOException {
    String name = "James";
    UserPrincipal owner = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(name);
    Files.setOwner(path, owner);
  }

  public static void main(String[] args) throws IOException {
    getPosixPermissions(Paths.get("/Users/rein"));
  }

  public static void getPosixPermissions(Path path) throws IOException {
    Set<PosixFilePermission> perms = Files.getPosixFilePermissions(path);
    boolean ownerRead = perms.contains(PosixFilePermission.OWNER_READ);
    System.out.println(perms);
    System.out.println(PosixFilePermissions.toString(perms));
  }

  public static void setPosixPermissions(Path path) throws IOException {
    {
      Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
      perms.add(PosixFilePermission.OWNER_READ);
      perms.add(PosixFilePermission.OWNER_EXECUTE);
      Files.setPosixFilePermissions(path, perms);
    }

    {
      Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-xr-x");
      Files.setPosixFilePermissions(path, perms);
    }
  }

  public static void getBasicAttributes(Path path) throws IOException {
    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

    System.out.println("creationTime: " + attr.creationTime());
    System.out.println("lastAccessTime: " + attr.lastAccessTime());
    System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

    System.out.println("isDirectory: " + attr.isDirectory());
    System.out.println("isOther: " + attr.isOther());
    System.out.println("isRegularFile: " + attr.isRegularFile());
    System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
    System.out.println("size: " + attr.size());
  }

  public static void getBasicAttributeView(Path path) throws IOException {
    BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);
    FileTime lastModifiedTime = FileTime.fromMillis(System.currentTimeMillis());
    FileTime lastAccessTime = FileTime.fromMillis(System.currentTimeMillis());
    FileTime createTime = FileTime.fromMillis(System.currentTimeMillis());
    view.setTimes(lastModifiedTime, lastAccessTime, createTime);
  }

  public static void getDosAttributes(Path path) throws IOException {
    DosFileAttributes attr = Files.readAttributes(path, DosFileAttributes.class);
    boolean isReadOnly = attr.isReadOnly();
    boolean isHidden = attr.isHidden();
    boolean isArchive = attr.isArchive();
    boolean isSystem = attr.isSystem();
  }

  public static void setDosAttributes(Path path) throws IOException {
    Boolean isHidden = (Boolean) Files.getAttribute(path, "dos:hidden");
    Files.setAttribute(path, "dos:hidden", true);
  }

}
