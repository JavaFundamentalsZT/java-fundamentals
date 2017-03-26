package data.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAXBExample {

  private static final Logger log = LoggerFactory.getLogger(JAXBExample.class);

  public static void main(String[] args) throws Exception {
    Path dest = Paths.get("movies.jaxb.xml");
    writeBean(dest);
    readBean(dest);
  }

  private static void writeBean(Path dest) throws Exception {
    List<Movie> movies = new ArrayList<>();
    movies.add(new Movie("Interstellar", 2014));
    Data data = new Data(movies);
    JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);
    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

    // output pretty printed
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    try (Writer writer = Files.newBufferedWriter(dest)) {
      jaxbMarshaller.marshal(data, writer);
    }
  }

  private static void readBean(Path dest) throws Exception {
    JAXBContext jaxbContext = JAXBContext.newInstance(Data.class);

    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    try (Reader reader = Files.newBufferedReader(dest)) {
      Data data = (Data) jaxbUnmarshaller.unmarshal(reader);
      Movie movie = data.getMovies().get(0);
      log.info("Found movie {} ({})", movie.getTitle(), movie.getYear());
    }
  }
}
