package data.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@XmlRootElement(name = "movies")
public class Data implements Serializable {

  @XmlElement(name = "movie")
  private final List<Movie> movies;

  @SuppressWarnings("unused")
  public Data() {
    this.movies = null;
  }

  public Data(List<Movie> movies) {
    this.movies = Collections.unmodifiableList(movies);
  }


  public List<Movie> getMovies() {
    return movies;
  }
}
