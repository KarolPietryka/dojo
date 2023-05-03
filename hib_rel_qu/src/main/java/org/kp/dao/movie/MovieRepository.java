package org.kp.dao.movie;

import org.kp.dao.movie.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    @Query("SELECT m FROM MovieEntity m WHERE m.genres = :genre")
    List<MovieEntity> findByGenre(@Param("genre") String genre);

    @Query("SELECT m FROM MovieEntity m WHERE m.releaseYear = :year")
    List<MovieEntity> findByReleaseYear(@Param("year") int year);

}
