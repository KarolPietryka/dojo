package org.kp.dao.director.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kp.dao.genre.Genre;
import org.kp.dao.movie.entity.MovieEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
scope = Director.class)
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Size(max = 255)
    private String name;

    @Column(name = "number_of_movies")
    private int numberOfMovies;
    @OneToMany(mappedBy = "director")
    private List<MovieEntity> movies;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "speciality_genre")
    private Genre specialityGenre;
    public Director(String name, int numberOfMovies, List<MovieEntity> movies, Genre specialityGenre) {
        this.name = name;
        this.numberOfMovies = numberOfMovies;
        this.movies = movies;
        this.specialityGenre = specialityGenre;
    }
    private Director(long id, String name, int numberOfMovies, List<MovieEntity> movies, Genre specialityGenre) {
        this.name = name;
        this.numberOfMovies = numberOfMovies;
        this.movies = movies;
        this.specialityGenre = specialityGenre;
    }

    public static class DirectorBuilder {
        public DirectorBuilder from(Director director) {
            return this.name(director.getName())
                    .numberOfMovies(director.getNumberOfMovies())
                    .movies(new ArrayList<>(director.getMovies()))
                    .specialityGenre(Genre.builder().from(director.getSpecialityGenre()).build());
        }
        //Override so can not be set
        public DirectorBuilder id(Director director){ return this; }
    }
}
