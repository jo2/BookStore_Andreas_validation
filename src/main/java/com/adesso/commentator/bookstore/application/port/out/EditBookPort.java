package com.adesso.commentator.bookstore.application.port.out;

import com.adesso.commentator.bookstore.domain.Book;

public interface EditBookPort {

    Book editBook(Book book);

}
