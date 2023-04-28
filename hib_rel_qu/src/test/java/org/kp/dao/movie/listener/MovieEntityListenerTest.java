package org.kp.dao.movie.listener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kp.dao.director.Director;
import org.kp.dao.director.DirectorRepository;
import org.kp.dao.genre.Genre;
import org.kp.dao.genre.GenreRepository;
import org.kp.dao.movie.entity.MovieEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class MovieEntityListenerTest {
  @Mock
  private GenreRepository genreRepository;
  @Mock
  private DirectorRepository directorRepository;

  @InjectMocks
  private MovieEntityListener movieEntityListener;
  @Test
  void onMovieCreated() {
      List<Genre> genres = new ArrayList<>();
      Genre genre = Genre.builder()
              .genre("Action")
              .directors(new ArrayList<>())
              .build();
      genres.add(genre);
      Director director = new Director("Name1", 0, new ArrayList<>(), null );
      MovieEntity movie = new MovieEntity("Title1", director, "1999", genres);
      when(directorRepository.save(any())).thenReturn(director);
      when(genreRepository.save(any())).thenReturn(genre);
      // Invoke the method under test
      movieEntityListener.onMovieCreated(movie);

      // Verify that the DirectorRepository and GenreRepository were called with the expected arguments
      verify(directorRepository, times(1)).save(
              Director.builder()
                      .from(director)
                      .numberOfMovies(director.getNumberOfMovies() + 1)
                      .lastMovieGenres(genre)
                      .build()
      );

      Set<Director> updatedDirectors = new HashSet<>(genre.getDirectors());
      updatedDirectors.add(director);
      verify(genreRepository, times(1)).save(
              Genre.builder()
                      .from(genre)
                      .directors(new ArrayList<>(updatedDirectors))
                      .build()
      );
  }
}