package org.kp.dao.book.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "books")
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    @Size(max = 255)
    private final String title;
    @NotBlank
    @Size(max = 255)
    private final String author;
    @NotBlank
    @Lob
    private final String description;
    @NotNull
    @Digits(integer = 8, fraction = 2)
    BigDecimal price;

    @JsonCreator
    public Book(@JsonProperty("title") String title,
                @JsonProperty("author") String author,
                @JsonProperty("description") String description,
                @JsonProperty("price") BigDecimal price) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
    }
    public Book(){
        this.title = "";
        this.author = "";
        this.description = "";
        this.price = BigDecimal.ZERO;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        return Objects.equals(id, other.id)
                && Objects.equals(title, other.title)
                && Objects.equals(author, other.author)
                && Objects.equals(description, other.description)
                && Objects.equals(price, other.price);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, description, price);
    }
}
