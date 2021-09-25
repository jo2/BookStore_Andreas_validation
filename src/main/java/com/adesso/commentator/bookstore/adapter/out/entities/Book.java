package com.adesso.commentator.bookstore.adapter.out.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Book {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="book_id")
    public Long id;

    @Column(name="title")
    public String title;

    @Column(name="author")
    public String author;

    @Column(name="price")
    public double price;

    @Column(name="publication_year")
    public int publicationYear;

    @Column(name="stock_amount")
    public int stockAmount;
}
