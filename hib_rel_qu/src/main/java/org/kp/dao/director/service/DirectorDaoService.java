package org.kp.dao.director.service;

import org.kp.dao.director.DirectorRepository;
import org.kp.dao.director.entity.Director;
import org.kp.dao.genre.Genre;
import org.kp.dao.genre.GenreRepository;
import org.kp.dao.movie.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DirectorDaoService {
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public DirectorDaoService(DirectorRepository directorRepository,
                              GenreRepository genreRepository) {
        this.directorRepository = directorRepository;
        this.genreRepository = genreRepository;
    }

    public void updateOnMovieSave(MovieEntity movie){
        Director director = movie.getDirector();


        List<MovieEntity> newMovieSet = new ArrayList<>(director.getMovies());
        newMovieSet.add(movie);
        Genre specialityGenre = newMovieSet
                .stream()
                .flatMap(it -> it.getGenres().stream())
                .collect(Collectors.groupingBy(Genre::getGenre, Collectors.counting())).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(genreRepository::findByGenre)
                .orElse(Collections.emptyList()).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No genres for movie"));

        directorRepository.save(
            Director.builder()
                .from(director)
                .movies(newMovieSet)
                .numberOfMovies(director.getNumberOfMovies() + 1)
                .specialityGenre(specialityGenre)
                .build());
    }
}
