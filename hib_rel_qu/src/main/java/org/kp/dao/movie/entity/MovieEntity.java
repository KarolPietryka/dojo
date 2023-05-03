package org.kp.dao.movie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import org.kp.dao.director.entity.Director;
import org.kp.dao.genre.Genre;
import org.kp.dao.movie.listener.MovieEntityListener;

import java.util.List;

@Entity
@EntityListeners(MovieEntityListener.class)
@Getter
@Builder
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Size(max = 255)
    private final String title;
    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private final Director director;
    @Min(value = 1900, message = "Value for year is to low")
    private final int releaseYear;
    //@NotEmpty
    @ManyToMany()
    private final List<Genre> genres;
    public MovieEntity(String title, Director director, int releaseYear, List<Genre> genres) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genres = genres;
    }

    private MovieEntity(long id, String title, Director director, int releaseYear, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genres = genres;
    }

    public static class MovieEntityBuilder{
        public MovieEntityBuilder from(MovieEntity movie){
            return this.title(movie.getTitle())
            .director(movie.getDirector())
            .releaseYear(movie.getReleaseYear())
            .genres(movie.getGenres());
        }
    }
}
