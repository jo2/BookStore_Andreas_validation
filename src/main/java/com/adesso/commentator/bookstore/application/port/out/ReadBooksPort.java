package com.adesso.commentator.bookstore.application.port.out;

import com.adesso.commentator.bookstore.domain.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ReadBooksPort")
public interface ReadBooksPort {

    List<Book> readAllBooks();
    Book readBookById(long bookId);
    boolean existsBookByTitleAndAuthor(String title, String author);
    Long readIdByTitleAndAuthor(String title, String author);
}
