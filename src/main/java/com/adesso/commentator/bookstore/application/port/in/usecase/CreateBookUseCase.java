package com.adesso.commentator.bookstore.application.port.in.usecase;

import com.adesso.commentator.bookstore.domain.Book;

public interface CreateBookUseCase {

    Book createBook(Book book);
}
