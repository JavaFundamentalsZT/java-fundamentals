package data.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public final class Movie {
  @XmlAttribute
  private final String title;

  @XmlAttribute
  private final int year;

  @SuppressWarnings("unused")
  public Movie() {
    this(null, 0);
  }

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
