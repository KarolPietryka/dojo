package org.kp.dao.movie.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kp.dao.director.entity.Director;
import org.kp.dao.genre.Genre;
import org.kp.dao.movie.listener.MovieEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@EntityListeners(MovieEntityListener.class)
@Getter
@Builder
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
scope = MovieEntity.class)
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Size(max = 255)
    private String title;
    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Director director;
    @Min(value = 1900, message = "Value for year is to low")
    private int releaseYear;
    //@NotEmpty
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Genre> genres;
    //@JsonCreator
    public MovieEntity(
            String title,
            Director director,
            int releaseYear,
            List<Genre> genres) {
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
            .director(Director.builder().from(movie.getDirector()).build())
            .releaseYear(movie.getReleaseYear())
            .genres(movie.getGenres().stream().map(it -> Genre.builder().from(it).build()).collect(Collectors.toList()));
        }
    }
}
