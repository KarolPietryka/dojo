package org.kp.service.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kp.dao.book.BookRepo;
import org.kp.dao.book.entity.Book;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
//@DataJpaTest
class BookServiceTest {
    @Mock
    private BookRepo bookRepo;
    @InjectMocks
    private BookService bookService;

    @Test
    void getBookByTitle() {
        Book bookEntity = new Book(
                "Test Book",
                "Test Author",
                "Test Description",
                BigDecimal.valueOf(19.99));

        when(bookRepo.findByTitle("Test Book")).thenReturn(Arrays.asList(bookEntity));

        // Act
        List<Book> books = bookService.getBookByTitle("Test Book");

        // Assert
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(1);
        Book book = books.stream().findFirst().orElseThrow(() -> new AssertionError("Empty list"));
        assertThat(book.getTitle()).isEqualTo("Test Book");
        assertThat(book.getAuthor()).isEqualTo("Test Author");
        assertThat(book.getDescription()).isEqualTo("Test Description");
        assertThat(book.getPrice()).isEqualTo(BigDecimal.valueOf(19.99));
    }

    @Test
    void createBook() {
        Book bookEntity = new Book(
                "Test Book",
                "Test Author",
                "Test Description",
                BigDecimal.valueOf(19.99));

        when(bookRepo.save(bookEntity)).thenReturn(bookEntity);

        // Act
        Book savedBookEntity = bookService.createBook(bookEntity);

        // Assert
        assertThat(savedBookEntity).isNotNull();
        assertThat(savedBookEntity.getTitle()).isEqualTo("Test Book");
        assertThat(savedBookEntity.getAuthor()).isEqualTo("Test Author");
        assertThat(savedBookEntity.getDescription()).isEqualTo("Test Description");
        assertThat(savedBookEntity.getPrice()).isEqualTo(BigDecimal.valueOf(19.99));
    }
}