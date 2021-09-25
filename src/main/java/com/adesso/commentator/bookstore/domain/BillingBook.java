package com.adesso.commentator.bookstore.domain;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public @Data class BillingBook {

    private final Long id;

    private Long billId;

    private long bookId;

    private String title;

    private String author;

    private int publicationYear;

    private int orderAmount;

    private double discount;

    private double price;

    private double totalAmount;

    public BillingBook(@Valid Book book, @Min(1) @Max(6) int orderAmount, @DecimalMin("0") @DecimalMax("20") double discount) {
        this.id = null;
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publicationYear = book.getPublicationYear();

        this.orderAmount = orderAmount;

        this.discount = discount;

        this.price = book.getPrice();

        this.totalAmount = Math.round(price * orderAmount * (1-(discount/100))*100)/100.0;
    }
}
