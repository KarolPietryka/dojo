package org.kp.dao.genre;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.kp.dao.director.Director;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@EqualsAndHashCode(exclude = "id")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Size(max = 255)
    private final String genre;

    @OneToMany(mappedBy = "specialty_genre")
    private final List<Director> directors;

    public Genre(String genre, List<Director> directors) {
        this.genre = genre;
        this.directors = directors;
    }
    private Genre(long id, String genre, List<Director> directors) {
        this.genre = genre;
        this.directors = directors;
    }
    public static class GenreBuilder {
        public GenreBuilder from(Genre genre){
            return this.genre(genre.getGenre())
                    .directors(new ArrayList<>(genre.getDirectors()));
        }
    }
}
