package data.serial;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaSerialExample {

  private static final Logger log = LoggerFactory.getLogger(JavaSerialExample.class);

  public static void main(String[] args) throws Exception {
    Path dest = Paths.get("movies.serial");
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
    try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(dest)))) {
      out.writeObject(data);
    }
  }

  private static void readRaw(Path src) throws Exception {
    Data data;
    try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(src)))) {
      data = (Data) in.readObject();
    }
    Movie movie = data.movies.get(0);
    log.info("Found movie {} ({})", movie.title, movie.year);
  }

}
