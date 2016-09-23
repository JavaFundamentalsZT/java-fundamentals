package desktop;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class OpenInBrowser {

  public static void main(String[] args) throws IOException {
    Desktop d = Desktop.getDesktop(); // May throw UnsupportedOperationException or HeadlessException
    if (d.isSupported(Desktop.Action.BROWSE)) {
      URI uri = URI.create("http://jf.0t.ee");
      d.browse(uri); // May throw IOException
    }
  }

}
