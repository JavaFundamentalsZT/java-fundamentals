package data.xml;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * See https://docs.oracle.com/javase/tutorial/jaxp/dom/
 * See https://docs.oracle.com/javase/tutorial/jaxp/xslt/
 */
public class DomExample {

  private static final Logger log = LoggerFactory.getLogger(DomExample.class);

  public static void main(String[] args) throws Exception {
    Path dest = Paths.get("movies.xml");
    write(dest);
    read(dest);
  }

  private static void write(Path dest) throws Exception {
    Document doc = create();
    persist(dest, doc);
    String s = FileUtils.readFileToString(dest.toFile(), StandardCharsets.UTF_8);
    System.out.println(s);
  }

  private static Document create() throws Exception {
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = builder.newDocument();
    Element movies = doc.createElement("movies");
    {
      {
        Element movie = doc.createElement("movie");
        movie.setAttribute("title", "Interstellar");
        movie.setAttribute("year", "2014");
        movies.appendChild(movie);
      }
      doc.appendChild(movies);
    }
    return doc;
  }

  private static void persist(Path dest, Document doc) throws Exception {
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(dest.toFile());
    transformer.transform(source, result);
  }

  private static void read(Path src) throws Exception {
    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = builder.parse(src.toFile());

    NodeList movies = doc.getElementsByTagName("movie");
    log.info("Movies: {}", movies.getLength());
    for (int i = 0; i < movies.getLength(); i++) {
      Element movie = (Element) movies.item(i);
      readMovie(movie);
    }

    print(doc);
  }

  static void readMovie(Element movie) {
    log.info("Movie: {}", movie);
    String title = movie.getAttribute("title");
    String year = movie.getAttribute("year");
    log.info("Found movie {} ({})", title, year);
  }

  private static void print(Document doc) throws Exception {
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(System.out);
    transformer.transform(source, result);
  }

}
