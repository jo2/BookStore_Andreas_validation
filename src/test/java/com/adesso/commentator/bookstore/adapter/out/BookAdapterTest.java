package com.adesso.commentator.bookstore.adapter.out;

import com.adesso.commentator.bookstore.adapter.out.repositories.BookRepository;
import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.domain.Book;
import com.adesso.commentator.bookstore.error.EntityWithIdNotFondException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookAdapterTest {

    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookAdapter bookAdapter;

    @Test
    void createBook() {
        Book book = MockData.getMockedBook();
        com.adesso.commentator.bookstore.adapter.out.entities.Book dto = Mapper.toDto(book);

        when(repository.save(any())).thenReturn(dto);

        assertThat(bookAdapter.createBook(book)).isEqualTo(book);
    }

    @Test
    void deleteBookById_existing() {
        bookAdapter.deleteBookById(1);

        verify(repository).deleteById(1L);
    }

    @Test
    void deleteBookById_notExisting() {
        doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(1L);

        assertThatThrownBy(() -> bookAdapter.deleteBookById(1L))
                .isInstanceOf(EntityWithIdNotFondException.class);
    }

    @Test
    void readAllBooks() {
        List<Book> books = MockData.getMockedBooks();
        List<com.adesso.commentator.bookstore.adapter.out.entities.Book> dtos = books.stream().map(Mapper::toDto).collect(Collectors.toList());

        when(repository.findAll()).thenReturn(dtos);

        assertThat(bookAdapter.readAllBooks()).containsAll(books);
    }

    @Test
    void readBookById() {
        Book book = MockData.getMockedBook();
        com.adesso.commentator.bookstore.adapter.out.entities.Book dto = Mapper.toDto(book);

        when(repository.findById(book.getId())).thenReturn(Optional.of(dto));

        assertThat(bookAdapter.readBookById(book.getId())).isEqualTo(book);
    }

    @Test
    void readIdByTitleAndAuthor_existing() {
        Book book = MockData.getMockedBook();
        when(repository.findBookByTitleAndAuthor(book.getTitle(), book.getAuthor())).thenReturn(Mapper.toDto(book));
        assertThat(bookAdapter.readIdByTitleAndAuthor(book.getTitle(), book.getAuthor())).isEqualTo(book.getId());
    }

    @Test
    void readIdByTitleAndAuthor_notExisting() {
        Book book = MockData.getMockedBook();
        when(repository.findBookByTitleAndAuthor(book.getTitle(), book.getAuthor())).thenReturn(null);
        assertThat(bookAdapter.readIdByTitleAndAuthor(book.getTitle(), book.getAuthor())).isNull();
    }

    @Test
    void existsBookByTitleAndAuthor_True() {
        String title = "ti", author = "au";

        when(repository.existsBookByTitleAndAuthor(title, author)).thenReturn(true);

        assertThat(bookAdapter.existsBookByTitleAndAuthor(title, author)).isTrue();
    }

    @Test
    void existsBookByTitleAndAuthor_False() {
        String title = "ti", author = "au";

        when(repository.existsBookByTitleAndAuthor(title, author)).thenReturn(false);

        assertThat(bookAdapter.existsBookByTitleAndAuthor(title, author)).isFalse();
    }

    @Test
    void editBook() {
        Book book = MockData.getMockedBook();
        com.adesso.commentator.bookstore.adapter.out.entities.Book dto = Mapper.toDto(book);

        when(repository.save(any())).thenReturn(dto);

        assertThat(bookAdapter.editBook(book)).isEqualTo(book);
    }
}
