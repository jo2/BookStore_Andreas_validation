package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.application.port.in.usecase.CreateBookUseCase;
import com.adesso.commentator.bookstore.application.port.out.CreateBookPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@AllArgsConstructor
public class CreateBookService implements CreateBookUseCase {

    private final CreateBookPort createBookPort;
    private final ReadBooksPort readBooksPort;

    @Override
    public Book createBook(@Valid Book book) {
        if (readBooksPort.existsBookByTitleAndAuthor(book.getTitle(), book.getAuthor()))
            throw new IllegalArgumentException("The combination of author and title must be unique");
        return createBookPort.createBook(book);
    }
}
