package com.adesso.commentator.bookstore.application.port.out;

import com.adesso.commentator.bookstore.domain.Book;

public interface CreateBookPort {
    Book createBook(Book book);
}
