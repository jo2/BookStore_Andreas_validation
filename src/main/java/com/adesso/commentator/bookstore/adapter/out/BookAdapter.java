package com.adesso.commentator.bookstore.adapter.out;

import com.adesso.commentator.bookstore.adapter.out.repositories.BookRepository;
import com.adesso.commentator.bookstore.application.port.out.CreateBookPort;
import com.adesso.commentator.bookstore.application.port.out.DeleteBookPort;
import com.adesso.commentator.bookstore.application.port.out.EditBookPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Book;
import com.adesso.commentator.bookstore.error.EntityWithIdNotFondException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component("Book adapter")
public class BookAdapter implements ReadBooksPort, CreateBookPort, DeleteBookPort, EditBookPort {

    private final BookRepository repository;

    @Override
    public Book createBook(Book book) {
        return Mapper.toDomain(repository.save(Mapper.toDto(book)));
    }

    @Override
    public void deleteBookById(long bookId) {
        try {
            repository.deleteById(bookId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityWithIdNotFondException(Book.class, bookId, e.getLocalizedMessage());
        }
    }

    @Override
    public List<Book> readAllBooks() {
        List<com.adesso.commentator.bookstore.adapter.out.entities.Book>
                books = new ArrayList<>();
        repository.findAll().forEach(books::add);
        return books.stream()
                .map(Mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Book readBookById(long bookId) {
        return repository.findById(bookId)
                .map(Mapper::toDomain)
                .orElse(null);
    }

    @Override
    public Long readIdByTitleAndAuthor(String title, String author) {
        com.adesso.commentator.bookstore.adapter.out.entities.Book
                b = repository.findBookByTitleAndAuthor(title, author);
        return b == null? null: b.id;
    }

    @Override
    public boolean existsBookByTitleAndAuthor(String title, String author) {
        return repository.existsBookByTitleAndAuthor(title, author);
    }

    @Override
    public Book editBook(Book book) {
        return Mapper.toDomain(repository.save(Mapper.toDto(book)));
    }
}
