package file;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class FileURL {

  public static void main(String[] args) throws MalformedURLException {
    File file = null;
    URL url = file.toURI().toURL();
  }

}
