package com.adesso.commentator.bookstore.application.port.in.query;

import com.adesso.commentator.bookstore.domain.Book;

import java.util.List;

public interface ReadBooksQuery {

    Book readBookById(long id);

    List<Book> readAllBooks();

    boolean existsBookByTitleAndAuthor(String title, String author);

}
