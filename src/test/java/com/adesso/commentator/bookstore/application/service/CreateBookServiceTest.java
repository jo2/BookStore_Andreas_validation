package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.application.port.out.CreateBookPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CreateBookServiceTest {

    @Mock
    private CreateBookPort createBookPort;

    @Mock
    private ReadBooksPort readBooksPort;

    @InjectMocks
    private CreateBookService createBookService;

    @Test
    void createBook_valid() {
        Book book = MockData.getMockedBook();

        when(readBooksPort.existsBookByTitleAndAuthor("ti", "au")).thenReturn(false);

        when(createBookPort.createBook(book)).thenReturn(book);

        assertThat(createBookService.createBook(book)).isEqualTo(book);
    }

    @Test
    void createBook_duplicateBook() {
        Book book = MockData.getMockedBook();

        when(readBooksPort.existsBookByTitleAndAuthor("ti", "au")).thenReturn(true);

        when(createBookPort.createBook(book)).thenReturn(book);

        assertThatThrownBy(() -> createBookService.createBook(book))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The combination of author and title must be unique");

    }
}
