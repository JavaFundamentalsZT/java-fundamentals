package data.json;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import data.Data;
import data.Movie;

public class JacksonExample {
  private static final Logger log = LoggerFactory.getLogger(JacksonExample.class);

  public static void main(String[] args) throws Exception {
    Path dest = Paths.get("movies.jackson.json");
    writeBean(dest);
    readRaw(dest);
  }

  private static void writeBean(Path dest) throws Exception {
    List<Movie> movies = new ArrayList<>();
    movies.add(new Movie("Interstellar", 2014));
    Data data = new Data(movies);
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    try (Writer writer = Files.newBufferedWriter(dest)) {
      mapper.writeValue(writer, data);
    }
  }

  private static void readRaw(Path src) throws Exception {
    Data data;
    ObjectMapper mapper = new ObjectMapper();
    try (Reader reader = Files.newBufferedReader(src)) {
      data = mapper.readValue(reader, Data.class);
    }
    Movie movie = data.getMovies().get(0);
    log.info("Found movie {} ({})", movie.getTitle(), movie.getYear());
  }
}
