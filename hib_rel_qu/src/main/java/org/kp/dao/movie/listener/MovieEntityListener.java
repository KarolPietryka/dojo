package org.kp.dao.movie.listener;

import jakarta.persistence.PostPersist;
import org.kp.dao.director.Director;
import org.kp.dao.director.DirectorRepository;
import org.kp.dao.genre.Genre;
import org.kp.dao.genre.GenreRepository;
import org.kp.dao.movie.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MovieEntityListener {
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    MovieEntityListener(DirectorRepository directorRepository, GenreRepository genreRepository) {
        this.directorRepository = directorRepository;
        this.genreRepository = genreRepository;
    }
    @PostPersist
    public void onMovieCreated(MovieEntity movie) {
        Director director = movie.getDirector();
        directorRepository.save(
            Director.builder()
                .from(director)
                .numberOfMovies(director.getNumberOfMovies() + 1)
                .lastMovieGenres(movie.getGenres().stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Empty movie genre list")))
                .build());
        movie.getGenres().forEach(genreToUpdate -> {
            Set<Director> updatedDirectors = new HashSet<>(genreToUpdate.getDirectors());
            updatedDirectors.add(movie.getDirector());
            genreRepository.save(Genre.builder()
                .from(genreToUpdate)
                .directors(new ArrayList<>(updatedDirectors))
                .build()
            );
        });
    }
}
