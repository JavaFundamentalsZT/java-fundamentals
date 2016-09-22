package log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Placeholders {
  private static final Logger log = LoggerFactory.getLogger(Placeholders.class);
  public static void main(String[] args) throws IOException {
    Path dir = Paths.get(".");
    List<Path> paths = Files.walk(dir).collect(Collectors.toList());
    log.info("Found {} paths.", paths.size());
    log.trace("All paths " + paths); // paths.toString() is always invoked
    log.trace("All paths: {}", paths); // paths.toString() is only invoked if needed
  }
}
