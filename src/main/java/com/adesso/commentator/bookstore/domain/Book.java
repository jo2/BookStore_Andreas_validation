package com.adesso.commentator.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@AllArgsConstructor
public @Data class Book {

    private Long id;

    @Size(min=2, max=30)
    private String title;

    @Size(min=2, max=20)
    private String author;

    @DecimalMin("1.0")
    private double price;

    @Range(min=1000, max=2050L)
    private int publicationYear;

    @Min(0)
    private int stockAmount;
}
