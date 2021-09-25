package com.adesso.commentator.bookstore.application.port.in.query;

import com.adesso.commentator.bookstore.domain.Bill;

import java.util.List;

public interface ReadBillsQuery {

    Bill readBillById(long id);

    List<Bill> readAllBills();

}
