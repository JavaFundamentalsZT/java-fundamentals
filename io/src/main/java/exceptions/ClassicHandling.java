package exceptions;

import java.io.Closeable;
import java.io.IOException;

public class ClassicHandling {
  public static void main(String[] args) throws IOException {
    Closeable resource = null;
    try {
      resource = obtainResource();
      processResource(resource);    // <-- exception?
    }
    finally {
      if (resource != null) {
        resource.close();           // <-- exception?
      }
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
