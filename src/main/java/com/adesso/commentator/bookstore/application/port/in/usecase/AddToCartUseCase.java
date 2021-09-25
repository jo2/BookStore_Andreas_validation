package com.adesso.commentator.bookstore.application.port.in.usecase;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;

public interface AddToCartUseCase {

    void addToCart(BillBookDto book, String user);

}
