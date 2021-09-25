package com.adesso.commentator.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Bill {

    private final Long id;

    private LocalDateTime date;

    private final List<BillingBook> books;

    private double totalPrice;

    public Bill(List<BillingBook> books) {
        id = null;
        date = LocalDateTime.now();
        this.books = books;
    }
}
