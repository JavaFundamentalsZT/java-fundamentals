package data.jaxp;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * See https://docs.oracle.com/javase/tutorial/jaxp/xslt/xpath.html
 */
public class XPathExample {

  private static final Logger log = LoggerFactory.getLogger(XPathExample.class);

  public static void main(String[] args) throws Exception {
    Path dest = Paths.get("movies.xpath.jaxp");
    read(dest);
  }

  private static void read(Path src) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(src.toFile());

    XPath xpath = XPathFactory.newInstance().newXPath();
    {
      XPathExpression expr = xpath.compile("/movies/movie");
      Element movie = (Element) expr.evaluate(doc, XPathConstants.NODE);
      log.info("Result: {}", movie);
      DomExample.readMovie(movie);
    }
    {
      String title = (String) xpath.compile("/movies/movie/@title").evaluate(doc, XPathConstants.STRING);
      String year = (String) xpath.compile("/movies/movie/@year").evaluate(doc, XPathConstants.STRING);
      log.info("Found movie {} ({})", title, year);
    }
  }

}
