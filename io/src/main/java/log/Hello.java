package log;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hello {
  private static final Logger log = LoggerFactory.getLogger(Hello.class);
  public static void main(String[] args) throws IOException {
    String name = args[0];
    log.info("Hello {}", name);
  }
}
