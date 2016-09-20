package stream;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;


public class Adapters {

  public static void main(String[] args) throws IOException {
    {
      Reader reader = new FileReader("input.data");
    }
    {
      InputStream is = new FileInputStream("input.data");
      Reader reader = new InputStreamReader(is, "UTF-8");
    }
    
    OutputStream os = null;
    Writer writer = new OutputStreamWriter(os, "UTF-8");
  }
  
}
