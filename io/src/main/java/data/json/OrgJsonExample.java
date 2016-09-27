package data.json;

import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrgJsonExample {

  private static final Logger log = LoggerFactory.getLogger(OrgJsonExample.class);

  public static void main(String[] args) throws Exception {
    Path dest = Paths.get("movies.json");
    writeRaw(dest);
    writeBean(dest);
    readRaw(dest);
    // The library does not support readBean()
  }

  private static void writeRaw(Path dest) throws Exception {
    JSONObject doc = new JSONObject();
    {
      JSONArray movies = new JSONArray();
      {
        JSONObject movie = new JSONObject();
        movie.put("title", "Interstellar");
        movie.put("year", 2014);
        movies.put(movie);
      }
      doc.put("movies", movies);
    }
    try (Writer writer = Files.newBufferedWriter(dest)) {
      doc.write(writer);
    }
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
    JSONObject doc = new JSONObject(data);
    try (Writer writer = Files.newBufferedWriter(dest)) {
      doc.write(writer);
    }
  }

  private static void readRaw(Path src) throws Exception {
    JSONObject doc = new JSONObject(FileUtils.readFileToString(src.toFile(), StandardCharsets.UTF_8));
    log.info("Read: {}", doc);
    JSONArray movies = doc.getJSONArray("movies");
    log.info("Movies: {}", movies);
    JSONObject movie = movies.getJSONObject(0);
    String title = movie.getString("title");
    int year = movie.getInt("year");
    log.info("Found movie {} ({})", title, year);
  }

}
