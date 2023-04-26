package org.kp.rest.book;

import org.kp.dao.book.entity.Book;
import org.kp.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;
    @Autowired
    public BookController(
        BookService bookService
    ){
        this.bookService = bookService;
    }
    @GetMapping("/getByTitle")
    public ResponseEntity<List<Book>> getByTitle(@RequestParam String title){
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.created(URI.create("/book/" + createdBook.getId())).body(createdBook);
    }
}
