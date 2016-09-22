package log;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCheck {
  private static final Logger log = LoggerFactory.getLogger(FileCheck.class);
  boolean contentEquals(Path f1, Path f2) {
    log.debug("Comparing {} and {}", f1, f2);
    try {
      return FileUtils.contentEquals(f1.toFile(), f2.toFile());
    }
    catch (IOException e) {
      log.debug("Could not compare {} and {}", f1, f2, e);
      return false;
    }
  }

}
