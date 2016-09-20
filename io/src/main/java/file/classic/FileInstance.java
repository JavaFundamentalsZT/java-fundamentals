package file.classic;

import java.io.File;

public class FileInstance {

  public static void main(String[] args) {
    new File("c:\\example\\data\\input.txt");
    new File("/example/data/input.txt");
    new File("example\\data\\input.txt");
    new File("example/data/input.txt");
    new File("example" + File.separator + "data" + File.separator + "input.txt");
    new File(new File("example", "data"), "input.txt");
  }

}
