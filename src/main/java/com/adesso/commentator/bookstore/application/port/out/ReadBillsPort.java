package com.adesso.commentator.bookstore.application.port.out;

import com.adesso.commentator.bookstore.domain.Bill;

import java.util.List;

public interface ReadBillsPort {

    Bill readBillById(long billId);

    List<Bill> readAllBills();

}
