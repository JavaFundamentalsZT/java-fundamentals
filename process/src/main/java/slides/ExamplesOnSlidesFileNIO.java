package slides;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExamplesOnSlidesFileNIO {

public static void main(String[] args) throws Exception {
  getPosixPermissions(Paths.get("pom.xml"));
}

public static void paths() throws IOException {
int i = 1;
p(i++, Paths.get("c:\\example\\data\\input.txt"));
p(i++, Paths.get("/example/data/input.txt"));
p(i++, Paths.get("example/data/input.txt"));
p(i++, Paths.get("example\\data\\input.txt"));
p(i++, Paths.get("example", "data", "input.txt"));
p(i++, Paths.get("/", "example", "data", "input.txt"));
p(i++, Paths.get(URI.create("file:/example/data/input.txt")));
}

private static void p(int i, Path path) {
  System.out.format("%s. %-25s -> root=%-10s count=%s parent=%-15s file=%-25s\n", 
      i, path, path.getRoot(), path.getNameCount(), path.getParent(), path.getFileName());
}

//on the slide, rename to "class Path"
interface MyPath {
  String toString();
  Path getFileName();
  Path getName(int index);
  int getNameCount();
  Path subpath(int beginIndex, int endIndex);
  Path getParent();
  Path getRoot();
  //continues on the next slide
}

//on the slide, rename to "class Path"
interface MyPath2 {
  Path normalize();
  boolean isAbsolute();
  Path toAbsolutePath();
  Path resolve(Path other);
  Path relativize(Path other);
  File toFile();
  URI toUri();
  //continues on the next slide
}
interface MyPath3 {
  Path toRealPath(LinkOption... options) throws IOException;
  enum LinkOption { NOFOLLOW_LINKS; }
}

public static void followLinks() throws IOException {
  Path path = Paths.get("/");
path.toRealPath(); //follows symbolic links
path.toRealPath(LinkOption.NOFOLLOW_LINKS);
//doesn't follow symbolic links
}

//on the slide, rename to "class Files"
interface MyFiles {
  boolean exists(Path path, LinkOption... options);
  boolean notExists(Path path, LinkOption... options);
  boolean isReadable(Path path);
  boolean isWritable(Path path);
  boolean isExecutable(Path path);
  boolean isSameFile(Path path, Path path2);
  //continues on the next slide
}

//on the slide, rename to "class Files"
interface MyFiles2 {
  void delete(Path path) throws IOException;
  boolean deleteIfExists(Path path) throws IOException;
  //continues on the next slide
}

//on the slide, rename to "class Files"
interface MyFiles3 {
  Path copy(Path source, Path target, CopyOption... options);
  long copy(InputStream in, OutputStream out);
  long copy(InputStream in, Path target, CopyOption... options);
  long copy(Path source, OutputStream out);
  //continues on the next slide
}

//on the slide, rename to "StandardCopyOption"
enum MyStandardCopyOption implements CopyOption {
  REPLACE_EXISTING,
  COPY_ATTRIBUTES,
  ATOMIC_MOVE;
}

//on the slide, rename to "LinkOption"
enum MyLinkOption implements CopyOption {
  NOFOLLOW_LINKS;
}

//on the slide, rename to "class Files"
interface MyFiles4 {
  Path move(Path source, Path target, CopyOption... options);
}

public static void listDirectories(Path dir) {
try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
  for (Path file: stream)
    System.out.println(file.getFileName());
} catch (IOException | DirectoryIteratorException x) {
  System.err.println(x);
}
}

public static void listDirectoriesWithFilter(Path dir) throws IOException {
DirectoryStream<Path> stream = Files.newDirectoryStream(dir, new Filter<Path>() {
  public boolean accept(Path path) {
    return Files.isDirectory(path);
  }
});
for (Path file: stream)
  System.out.println(file.getFileName());
}

public static void listDirectoriesWithFilter2(Path dir) throws IOException {
  DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.class");
  for (Path file: stream)
    System.out.println(file.getFileName());
}

//on the slide, rename to "class Files"
interface MyFiles5 {
  byte[] readAllBytes(Path path);
  List<String> readAllLines(Path path, Charset cs);
  Path write(Path p, byte[] bytes, OpenOption... oo);
  Path write(Path path, Iterable<? extends CharSequence> lines, Charset c, OpenOption... oo);
}

//on the slide, rename to "class Files"
interface MyFiles6 {
  long size(Path path);
  boolean isDirectory(Path path, LinkOption... lo);
  boolean isRegularFile(Path path, LinkOption... lo);
  boolean isSymbolicLink(Path path);
  boolean isHidden(Path path);
  //to be continued
}

public static void lastModifiedTime(Path path) throws IOException {
{
FileTime time = Files.getLastModifiedTime(path);
long millis = time.toMillis();
System.out.println(millis);
}
{
long millis = System.currentTimeMillis();
FileTime time = FileTime.fromMillis(millis);
Files.setLastModifiedTime(path, time);
}
}

public static void fileOwner(Path path) throws IOException {
{
UserPrincipal owner = Files.getOwner(path);
String name = owner.getName();
System.out.println(name);
}
{
String name = "James";
UserPrincipal owner = path.getFileSystem().
    getUserPrincipalLookupService().
    lookupPrincipalByName(name);
Files.setOwner(path, owner);}
}

public static void getPosixPermissions(Path path) throws IOException {
Set<PosixFilePermission> perms =
  Files.getPosixFilePermissions(path);
boolean ownerRead = perms.contains(
  PosixFilePermission.OWNER_READ);

System.out.println(perms);
System.out.println(
  PosixFilePermissions.toString(perms));

System.out.println(ownerRead);
}

public static void setPosixPermissions(Path path) throws IOException {
{
Set<PosixFilePermission> perms =
  new HashSet<PosixFilePermission>();
perms.add(PosixFilePermission.OWNER_READ);
Files.setPosixFilePermissions(path, perms);
}{
Set<PosixFilePermission> perms =
  PosixFilePermissions.fromString("rwxr-xr-x");
Files.setPosixFilePermissions(path, perms);
}
}

//on the slide, rename to "class Files"
interface MyFiles7 {
  Object getAttribute(Path path, String attribute, LinkOption... options);
  Path setAttribute(Path path, String attribute, Object value, LinkOption... options);
  //to be continued
}
//on the slide, rename to "class Files"
interface MyFiles8 {

  <A extends BasicFileAttributes> A
  readAttributes(
    Path path,
    Class<A> type,
    LinkOption... options);
  
  //to be continued
}

//on the slide, rename to "class Files"
interface MyFiles9 {
  <V extends FileAttributeView> V
    getFileAttributeView(
      Path path,
      Class<V> type,
      LinkOption... options);
  
  //that's it
}

public static void readAttributes(Path path) throws IOException {
  DosFileAttributes attr = Files.readAttributes(
    path, DosFileAttributes.class);
  System.out.println(attr);
}

//on the slide, rename to "interface DosFileAttributes"
interface MyDosFileAttributes
    extends BasicFileAttributes {
  boolean isReadOnly();
  boolean isHidden();
  boolean isArchive();
  boolean isSystem();
}

}
