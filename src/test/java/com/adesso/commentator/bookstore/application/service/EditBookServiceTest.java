package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.application.port.out.EditBookPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EditBookServiceTest {

    @Mock
    private EditBookPort editBookPort;

    @Mock
    private ReadBooksPort readBooksPort;

    @InjectMocks
    private EditBookService editBookService;

    @Test
    void editBook_valid() {
        Book book = MockData.getMockedBook();

        when(readBooksPort.readIdByTitleAndAuthor(book.getTitle(), book.getAuthor())).thenReturn(book.getId());

        editBookService.editBook(book);

        verify(editBookPort).editBook(book);
    }

    @Test
    void editBook_DuplicateAuthorTitle() {
        Book book = MockData.getMockedBook();

        when(readBooksPort.readIdByTitleAndAuthor(book.getTitle(), book.getAuthor())).thenReturn(-1L);

        assertThatThrownBy(() ->
            editBookService.editBook(book)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContainingAll("Combination", "Title and Author");


    }
}
