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
import com.google.gson.Gson;

import data.Data;
import data.Movie;

public class GsonExample {

  private static final Logger log = LoggerFactory.getLogger(GsonExample.class);

  public static void main(String[] args) throws Exception {
    Path dest = Paths.get("movies.json");
    writeBean(dest);
    readRaw(dest);
  }

  private static void writeBean(Path dest) throws Exception {
    List<Movie> movies = new ArrayList<>();
    movies.add(new Movie("Interstellar", 2014));
    Data data = new Data(movies);
    try (Writer writer = Files.newBufferedWriter(dest)) {
      new Gson().toJson(data, writer);
    }
  }

  private static void readRaw(Path src) throws Exception {
//    Data data = new Gson().fromJson(FileUtils.readFileToString(src.toFile(), StandardCharsets.UTF_8), Data.class);
    Data data;
    try (Reader reader = Files.newBufferedReader(src)) {
      data = new Gson().fromJson(reader, Data.class);
    }
    Movie movie = data.getMovies().get(0);
    log.info("Found movie {} ({})", movie.getTitle(), movie.getYear());
  }

}
