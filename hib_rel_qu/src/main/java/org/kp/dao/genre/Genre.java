package org.kp.dao.genre;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.kp.dao.director.entity.Director;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@EqualsAndHashCode(exclude = "id")
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Size(max = 255)
    private  String genre;

    @OneToMany(mappedBy = "specialityGenre", cascade = CascadeType.PERSIST)
    private  List<Director> directors;

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
