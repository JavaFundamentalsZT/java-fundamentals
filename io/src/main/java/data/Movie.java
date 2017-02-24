package data;

import java.io.Serializable;

public final class Movie implements Serializable {
  private final String title;
  private final int year;

  public Movie(String title, int year) {
    this.title = title;
    this.year = year;
  }

  public String getTitle() {
    return title;
  }

  public int getYear() {
    return year;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Movie movie = (Movie) o;
    return getYear() == movie.getYear() && getTitle().equals(movie.getTitle());
  }

  @Override
  public int hashCode() {
    int result = getTitle().hashCode();
    result = 31 * result + getYear();
    return result;
  }
}
