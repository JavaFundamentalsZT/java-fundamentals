package nio2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by rein on 15/09/16.
 */
public class PathsFun {

  public static void main(String[] args) throws IOException {
    test(Paths.get("example", "data", "..", "input"));
    test(Paths.get("/", "home", "user", "..", "foo"));
    boolean b= false;
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

}
