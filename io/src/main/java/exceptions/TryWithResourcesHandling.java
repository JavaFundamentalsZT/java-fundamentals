package exceptions;

import java.io.Closeable;
import java.io.IOException;

public class TryWithResourcesHandling {
  public static void main(String[] args) throws IOException {
    try (Closeable resource = obtainResource()) {
      processResource(resource);    // <-- exception?
    }
  }

  private static void processResource(Closeable resource) throws IOException {
    // important calculations
    throw new IOException("error during processing!");
  }

  private static Closeable obtainResource() throws IOException {
    return () -> { throw new IOException("errror during closing!"); };
  }

}
