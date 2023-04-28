package org.kp.dao.movie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.kp.dao.director.Director;
import org.kp.dao.genre.Genre;
import org.kp.dao.movie.listener.MovieEntityListener;

import java.util.List;

@Entity
@EntityListeners(MovieEntityListener.class)
@Getter
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Size(max = 255)
    private final String title;
    @NotBlank
    @Size(max = 255)
    @ManyToOne
    private final Director director;
    @NotBlank
    @Min(value = 1900, message = "Value for year is to low")
    private final String releaseYear;
    @NotEmpty
    @ManyToMany
    private final List<Genre> genres;
    public MovieEntity(String title, Director director, String releaseYear, List<Genre> genres) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genres = genres;
    }
}
