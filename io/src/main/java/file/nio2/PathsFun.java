package file.nio2;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by rein on 15/09/16.
 */
public class PathsFun {

  public static void main(String[] args) throws Exception {
    test(Paths.get("example", "data", "..", "input"));
    test(Paths.get("/", "home", "user", "..", "foo"));
    relativePaths();
    toFile();
    toUri();
    toRealPath();
  }

  private static void test(Path p) throws IOException {
    System.out.println(p);
    System.out.println(p.isAbsolute());
    System.out.println(p.getFileName());
    System.out.println(p.getParent());
    System.out.println(p.getRoot());
    System.out.println(p.normalize());
    System.out.println(p.toAbsolutePath());
    System.out.println(p.toFile());
    System.out.println(p.toUri());
//    System.out.println(p.toRealPath());
    System.out.println();
  }

  private static void relativePaths() throws MalformedURLException, URISyntaxException {
    Path home = Paths.get("/Users/mati");
    Path docAbsolute = home.resolve("Dropbox/Documents"); // /Users/mati/Dropbox/Documents
    Path docRelative = home.relativize(docAbsolute); // Dropbox/Documents
    System.out.println("home: " + home);
    System.out.println("docAbsolute: " + docAbsolute);
    System.out.println("docRelative: " + docRelative);
  }

  private static void toFile() {
    Path path = Paths.get("/Users/mati");
    File file = path.toFile();
    Path path2 = file.toPath();
  }

  private static void toUri() throws MalformedURLException, URISyntaxException {
    Path path = Paths.get("/Users/mati");
    System.out.println("Path: " + path);
    URI uri = path.toUri(); // file:///Users/mati
    System.out.println("URI: " + uri);
    URL url = uri.toURL(); // file:/Users/mati
    System.out.println("URL: " + url);
    URI uri2 = url.toURI(); // file:/Users/mati
    System.out.println("URI2: " + uri2);
    Path path2 = Paths.get(uri2); // /Users/mati
    System.out.println("Path2: " + path2);
  }

  private static void toRealPath() throws IOException {
    Path p = Paths.get("/usr/bin/../bin/java");
    System.out.println(p);
    Path p2 = p.toRealPath(); // /System/Library/Frameworks/JavaVM.framework/Versions/A/Commands/java
    System.out.println(p2);
    Path p3 = p.toRealPath(LinkOption.NOFOLLOW_LINKS); // /usr/bin/java
    System.out.println(p3);
  }

}
