package com.adesso.commentator.bookstore.application.port.out;

import com.adesso.commentator.bookstore.domain.Bill;

public interface CreateBillPort {

    Bill createBill(Bill bill);

}
