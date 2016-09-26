package data.json;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class GsonExample {

  private static final Logger log = LoggerFactory.getLogger(GsonExample.class);

  public static void main(String[] args) throws Exception {
    Path dest = Paths.get("movies.json");
    writeBean(dest);
    readRaw(dest);
  }

  private static void writeBean(Path dest) throws Exception {
    Data data = new Data();
    {
      List<Movie> movies = new ArrayList<>();
      {
        Movie movie = new Movie();
        movie.title = "Interstellar";
        movie.year = 2014;
        movies.add(movie);
      }
      data.movies = movies;
    }
//    String json = new Gson().toJson(data);
//    FileUtils.writeStringToFile(dest.toFile(), json, StandardCharsets.UTF_8);
    try (Writer writer = Files.newBufferedWriter(dest, StandardCharsets.UTF_8)) {
      new Gson().toJson(data, writer);
    }
  }

  private static void readRaw(Path src) throws Exception {
//    Data data = new Gson().fromJson(FileUtils.readFileToString(src.toFile(), StandardCharsets.UTF_8), Data.class);
    Data data;
    try (Reader reader = Files.newBufferedReader(src)) {
      data = new Gson().fromJson(reader, Data.class);
    }
    Movie movie = data.movies.get(0);
    log.info("Found movie {} ({})", movie.title, movie.year);
  }

}
