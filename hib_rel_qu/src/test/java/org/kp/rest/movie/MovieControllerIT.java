package org.kp.rest.movie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kp.dao.director.DirectorRepository;
import org.kp.dao.director.entity.Director;
import org.kp.dao.genre.Genre;
import org.kp.dao.genre.GenreRepository;
import org.kp.dao.movie.MovieRepository;
import org.kp.dao.movie.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class MovieControllerIT {

    private final MovieRepository movieRepository;
    private final TestRestTemplate restTemplate;

    @Autowired
    public MovieControllerIT(MovieRepository movieRepository,
                             GenreRepository genreRepository,
                             DirectorRepository directorRepository,
                             TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.movieRepository = movieRepository;
    }

    @BeforeEach
    void setUp(){
        movieRepository.deleteAll();
        List<Genre> genres = new ArrayList<>();
        Genre genre = Genre.builder()
                .genre("Action")
                .directors(new ArrayList<>())
                .build();
        genres.add(genre);
        Director director = new Director("Name1", 0, new ArrayList<>(), genre );
        MovieEntity movie = new MovieEntity("Title1", director, 1999, genres);

        movieRepository.save(movie);
        movieRepository.save(MovieEntity.builder().from(movie).title("Title2").build());
    }
    @Test
    void getByGenre() {
        ResponseEntity<MovieEntity[]> responseEntity = restTemplate.getForEntity(
                "/movie/getByGenre?genre=Action",
                MovieEntity[].class
        );
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().length).isEqualTo(2);
        assertThat(Arrays.stream(responseEntity.getBody()).allMatch(it->
            it.getReleaseYear() == 1999)
        ).isEqualTo(true);
        assertThat((int) Arrays.stream(responseEntity.getBody()).filter(it ->
                it.getTitle().equals("Title1")).count()
        ).isEqualTo(1);
        assertThat((int) Arrays.stream(responseEntity.getBody()).filter(it ->
                it.getTitle().equals("Title2")).count()
        ).isEqualTo(1);

    }
}