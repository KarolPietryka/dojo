package org.kp.dao.director;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.kp.dao.genre.Genre;
import org.kp.dao.movie.entity.MovieEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@EqualsAndHashCode(exclude = "id")
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Size(max = 255)
    private final String name;

    @Column(name = "number_of_movies")
    private final int numberOfMovies;
    @OneToMany(mappedBy = "director")
    private final List<MovieEntity> movies;

    @NotBlank
    @Column(name = "last_movie_genre")
    @ManyToOne
    private final Genre lastMovieGenres;

    public Director(String name, int numberOfMovies, List<MovieEntity> movies, Genre lastMovieGenres) {
        this.name = name;
        this.numberOfMovies = numberOfMovies;
        this.movies = movies;
        this.lastMovieGenres = lastMovieGenres;
    }
    private Director(long id, String name, int numberOfMovies, List<MovieEntity> movies, Genre lastMovieGenres) {
        this.name = name;
        this.numberOfMovies = numberOfMovies;
        this.movies = movies;
        this.lastMovieGenres = lastMovieGenres;
    }

    public static class DirectorBuilder {
        public DirectorBuilder from(Director director) {
            return this.name(director.getName())
                    .numberOfMovies(director.getNumberOfMovies())
                    .movies(new ArrayList<>(director.getMovies()));
        }
        //Override so can not be set
        public DirectorBuilder id(Director director){ return this; }
    }
}
