package com.adesso.commentator.bookstore.adapter.out;

import com.adesso.commentator.bookstore.adapter.out.entities.BillingBook;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.Book;

import java.util.stream.Collectors;

public class Mapper {

    private Mapper() {}

     public static com.adesso.commentator.bookstore.adapter.out.entities.Bill toDto(Bill bill) {
         if (bill == null) return null;
        return new com.adesso.commentator.bookstore.adapter.out.entities.Bill(
                bill.getId(),
                bill.getDate(),
                bill.getTotalPrice(),
                bill.getBooks().stream().map(Mapper::toDto).collect(Collectors.toList())
        );
    }

    public static BillingBook toDto(com.adesso.commentator.bookstore.domain.BillingBook book) {
         if (book == null) return null;
        return new BillingBook(
                book.getId(),
                book.getBillId(),
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getOrderAmount(),
                book.getDiscount(),
                book.getPrice(),
                book.getTotalAmount()
        );
    }

    public static Bill toDomain(com.adesso.commentator.bookstore.adapter.out.entities.Bill bill) {
         if (bill == null) return null;
        return new Bill(
                bill.id,
                bill.date,
                bill.books.stream().map(Mapper::toDomain).collect(Collectors.toList()),
                bill.totalPrice
        );
    }

    public static com.adesso.commentator.bookstore.domain.BillingBook toDomain(BillingBook book) {
         if (book == null) return null;
        return new com.adesso.commentator.bookstore.domain.BillingBook(
                new Book(
                        book.bookId,
                        book.title,
                        book.author,
                        book.price,
                        book.publicationYear,
                        0
                ),
                book.orderAmount,
                book.discount
        );
    }

    public static Book toDomain(com.adesso.commentator.bookstore.adapter.out.entities.Book bookDto) {
         if (bookDto == null) return null;
        return new Book(
                bookDto.id,
                bookDto.title,
                bookDto.author,
                bookDto.price,
                bookDto.publicationYear,
                bookDto.stockAmount
        );
    }

    public static com.adesso.commentator.bookstore.adapter.out.entities.Book toDto(Book book) {
         if (book == null) return null;
        return new com.adesso.commentator.bookstore.adapter.out.entities.Book(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getPublicationYear(),
                book.getStockAmount()
        );

    }

}
