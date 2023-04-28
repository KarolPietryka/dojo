package org.kp.dao.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    public List<Genre> findByGenre(String genre);
}
