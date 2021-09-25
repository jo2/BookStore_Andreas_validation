package com.adesso.commentator.bookstore.adapter.out.repositories;

import com.adesso.commentator.bookstore.adapter.out.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

    boolean existsBookByTitleAndAuthor(String title, String author);

    Book findBookByTitleAndAuthor(String title, String author);
}
