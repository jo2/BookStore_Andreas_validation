package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.application.port.in.query.ReadBooksQuery;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Book;
import com.adesso.commentator.bookstore.error.EntityWithIdNotFondException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class ReadBooksService implements ReadBooksQuery {

    private final ReadBooksPort readBooksPort;

    @Override
    public Book readBookById(long id) {
        Book b = readBooksPort.readBookById(id);
        if (b == null) throw new EntityWithIdNotFondException(Book.class, id, "Entity With id not found");
        return b;
    }

    @Override
    public List<Book> readAllBooks() {
        List<Book> books = readBooksPort.readAllBooks();
        books.sort(Comparator.comparing(Book::getTitle));
        return books;
    }

    @Override
    public boolean existsBookByTitleAndAuthor(String title, String author) {
        return readBooksPort.existsBookByTitleAndAuthor(title, author);
    }
}
