package com.adesso.commentator.bookstore.adapter.out.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bill_id")
    public Long id;

    @Column(name="date")
    public LocalDateTime date;

    @Column(name="total_price")
    public double totalPrice;

    @OneToMany(mappedBy = "billId")
    public List<BillingBook> books;
}
