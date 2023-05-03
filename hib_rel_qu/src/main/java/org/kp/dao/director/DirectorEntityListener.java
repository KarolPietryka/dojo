package org.kp.dao.director;

import jakarta.persistence.PostPersist;
import org.kp.dao.director.entity.Director;
import org.kp.dao.director.service.DirectorDaoService;
import org.kp.dao.genre.GenreRepository;
import org.kp.dao.movie.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class DirectorEntityListener {
    @Lazy
    @Autowired
    private DirectorDaoService directorDaoService;
    @Lazy
    @Autowired
    private GenreRepository genreRepository;


    @PostPersist
    public void onDirectorCreated(Director director) {
        System.out.println("test");
    }
}
