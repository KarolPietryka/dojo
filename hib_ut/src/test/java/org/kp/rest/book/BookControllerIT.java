package org.kp.rest.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kp.dao.book.BookRepo;
import org.kp.dao.book.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIT {
    @Autowired
    BookControllerIT(
        BookRepo bookRepo,
        TestRestTemplate restTemplate
    ){
        this.bookRepo = bookRepo;
        this.restTemplate = restTemplate;
    }
    private BookRepo bookRepo;
    private TestRestTemplate restTemplate;


    @BeforeEach
    void setUp() {
        bookRepo.deleteAll();
        bookRepo.save(new Book("title1", "author1", "description1", BigDecimal.valueOf(10.99)));
        bookRepo.save(new Book("title2", "author2", "description2", BigDecimal.valueOf(20.99)));
    }
    @Test
    void getByTitle() {
        ResponseEntity<Book[]> responseEntity = restTemplate.getForEntity(
                "/book/getByTitle?title=title1",
                Book[].class
        );
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().length).isEqualTo(1);
        assertThat(Arrays.stream(responseEntity.getBody())
                .findFirst()
                .orElseThrow(() ->new AssertionError("noFirest"))
                .getTitle()
        ).isEqualTo("title1");
    }

    @Test
    void createBook() {
        Book book = new Book("title3", "author3", "description3", BigDecimal.valueOf(30.99));
        ResponseEntity<Book> responseEntity = restTemplate.postForEntity(
                "/book",
                book,
                Book.class
        );
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getTitle()).isEqualTo(book.getTitle());
    }
}