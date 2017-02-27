package data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public final class Data implements Serializable {
  private final List<Movie> movies;

  public Data() {
    movies = null;
  }

  public Data(List<Movie> movies) {
    this.movies = Collections.unmodifiableList(movies);
  }

  public List<Movie> getMovies() {
    return movies;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Data data = (Data) o;
    return getMovies().equals(data.getMovies());
  }

  @Override
  public int hashCode() {
    return getMovies().hashCode();
  }
}
