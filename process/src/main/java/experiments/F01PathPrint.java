package experiments;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class F01PathPrint {

  public static void main(String[] args) {
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

}
