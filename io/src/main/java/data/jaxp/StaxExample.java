package data.jaxp;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * See https://docs.oracle.com/javase/tutorial/jaxp/stax/
 */
public class StaxExample {

  private static final Logger log = LoggerFactory.getLogger(StaxExample.class);

  public static void main(String[] args) throws Exception {
    Path dest = Paths.get("movies.saxt.jaxp");
    write(dest);
    read(dest);
  }

  private static void write(Path dest) throws Exception {
    XMLOutputFactory output = XMLOutputFactory.newInstance();
    try (Writer out = Files.newBufferedWriter(dest)) {
      XMLStreamWriter writer = output.createXMLStreamWriter(out);
      writer.writeStartDocument();
      {
        writer.writeStartElement("movies");
        {
          writer.writeEmptyElement("movie");
          writer.writeAttribute("title", "Interstellar");
          writer.writeAttribute("year", "2014");
        }
        writer.writeEndElement();
      }
      writer.writeEndDocument();
      writer.close();
    }
  }

  private static void read(Path src) throws Exception {
    XMLInputFactory f = XMLInputFactory.newInstance();
    try (Reader in = Files.newBufferedReader(src)) {
      XMLEventReader reader = f.createXMLEventReader(in);
      while (reader.hasNext()) {
        XMLEvent event = reader.nextEvent();
        if (event.isStartElement()) {
          StartElement se = (StartElement) event;
          if (se.getName().getLocalPart().equals("movie")) {
            String title = se.getAttributeByName(new QName("title")).getValue();
            String year = se.getAttributeByName(new QName("year")).getValue();
            log.info("Found movie {} ({})", title, year);
          }
        }
      }
    }
  }

}
