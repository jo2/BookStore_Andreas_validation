package com.adesso.commentator.bookstore.application.port.in.mapper;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.adapter.in.dto.BillDto;
import com.adesso.commentator.bookstore.application.port.in.query.ReadBooksQuery;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.BillingBook;
import com.adesso.commentator.bookstore.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MapperImpl implements Mapper {

    @Autowired
    private ReadBooksQuery readBooksQuery;

    @Override
    public Bill toDomain(BillDto dto) {
        return new Bill(
                dto.getBooks().stream().map(this::toDomain).collect(Collectors.toList())
        );
    }

    @Override
    public BillingBook toDomain(BillBookDto book) {
        Book b = readBooksQuery.readBookById(book.getId());
        if (b==null) throw new IllegalArgumentException("book must exist");
        return new BillingBook(
                b,
                book.getOrderAmount(),
                book.getDiscount()
        );
    }
}
