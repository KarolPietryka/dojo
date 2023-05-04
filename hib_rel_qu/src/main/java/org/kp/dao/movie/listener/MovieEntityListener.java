package org.kp.dao.movie.listener;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PostPersist;
import org.kp.dao.director.entity.Director;
import org.kp.dao.director.DirectorRepository;
import org.kp.dao.director.service.DirectorDaoService;
import org.kp.dao.genre.Genre;
import org.kp.dao.genre.GenreRepository;
import org.kp.dao.movie.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.*;

@Component
public class MovieEntityListener {
    @Lazy
    @Autowired
    private DirectorDaoService directorDaoService;
    @Lazy
    @Autowired
    private GenreRepository genreRepository;
    @Lazy
    @Autowired
    private EntityManager entityManager;

    @PostPersist
    public void onMovieCreated(MovieEntity movie) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                directorDaoService.updateOnMovieSave(movie);
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
        });

    }
}
