package org.kp.service.book;

import org.kp.dao.book.BookRepo;
import org.kp.dao.book.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepo bookRepo;
    @Autowired
    BookService(BookRepo bookRepo){
        this.bookRepo = bookRepo;
    }

    public List<Book> getBookByTitle(String title){
        return bookRepo.findByTitle(title);
    }
    public Book createBook(Book book){
        return bookRepo.save(book);
    }
}
