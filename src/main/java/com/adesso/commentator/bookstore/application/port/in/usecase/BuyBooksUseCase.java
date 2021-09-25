package com.adesso.commentator.bookstore.application.port.in.usecase;

import com.adesso.commentator.bookstore.domain.Bill;

public interface BuyBooksUseCase {

    Bill buyBooks(Bill bill);

}
