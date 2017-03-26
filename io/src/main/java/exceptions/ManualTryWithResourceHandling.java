package exceptions;

import java.io.Closeable;
import java.io.IOException;

public class ManualTryWithResourceHandling {
  public static void main(String[] args) throws IOException {
    Closeable resource = null;
    IOException myException = null;
    try {
      resource = obtainResource();
      processResource(resource);    // <-- exception?
    }
    catch (IOException e) {
      myException = e;
      throw e;
    }
    finally {
      if (resource != null) {
        if (myException != null) {
          try {
            resource.close();
          }
          catch (Throwable closingException) {
            myException.addSuppressed(closingException);
          }
        }
        else {
          resource.close();
        }
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
