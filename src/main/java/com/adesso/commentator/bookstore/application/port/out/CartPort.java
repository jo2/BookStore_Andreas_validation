package com.adesso.commentator.bookstore.application.port.out;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;

import java.util.List;

public interface CartPort {

    void addToCart(BillBookDto book, String user);
    void removeFromCart(BillBookDto book, String user);
    List<BillBookDto> getCart(String user);
    void clearCart(String user);

}
