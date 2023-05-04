package org.kp.rest.movie;

import org.kp.dao.genre.Genre;
import org.kp.dao.movie.MovieRepository;
import org.kp.dao.movie.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private final MovieRepository movieRepository;

    @Autowired
    MovieController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }
    @GetMapping("/getByGenre")
    ResponseEntity<List<MovieEntity>> getByGenre(@RequestParam String genre){
        return ResponseEntity.ok(movieRepository.findByGenre(genre));
    }
}
