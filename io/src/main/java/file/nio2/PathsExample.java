package file.nio2;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsExample {

  public static void main(String[] args) throws IOException {
    // Old API
    new File("c:\\example\\data\\input.txt");
    new File("/example/data/input.txt");
    new File("example\\data\\input.txt");
    new File("example/data/input.txt");
    new File("example" + File.separator + "data" + File.separator + "input.txt");
    new File(new File("example", "data"), "input.txt");

    p(new File("c:\\example\\data\\input.txt"));
    p(new File("/example/data/input.txt"));
    p(new File("example\\data\\input.txt"));
    p(new File("example/data/input.txt"));
    p(new File("example" + File.separator + "data" + File.separator + "input.txt"));
    p(new File(new File("example", "data"), "input.txt"));

    // New API
    Paths.get("c:\\example\\data\\input.txt");
    Paths.get("/example/data/input.txt");
    Paths.get("example/data/input.txt");
    Paths.get("example\\data\\input.txt");
    Paths.get("example", "data", "input.txt");
    Paths.get("/", "example", "data", "input.txt");
    Paths.get(URI.create("file:/example/data/input.txt"));

    p(Paths.get("c:\\example\\data\\input.txt"));
    p(Paths.get("/example/data/input.txt"));
    p(Paths.get("example/data/input.txt"));
    p(Paths.get("example\\data\\input.txt"));
    p(Paths.get("/", "example", "data", "input.txt"));
    p(Paths.get("example", "data", "input.txt"));
    p(Paths.get(URI.create("file:/example/data/input.txt")));
    
//    Path p = null;
//    Path f1 = p.toRealPath();
//    Path f2 = p.toRealPath(LinkOption.NOFOLLOW_LINKS);
    
  }
  
  private static void p(Object o) {
    System.out.println(o);
  }

}
