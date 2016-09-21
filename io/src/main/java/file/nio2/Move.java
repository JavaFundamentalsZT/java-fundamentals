package file.nio2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Move {

  public static void main(String[] args) throws IOException {
    Path src = Paths.get("unicorn");
    Path dest = Paths.get("unicorn.moved");

    Files.move(src, dest);

    Files.move(src, dest, StandardCopyOption.ATOMIC_MOVE);
    // May throw AtomicMoveNotSupportedException
  }

}
