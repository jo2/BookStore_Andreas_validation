package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.application.port.in.query.GetCartQuery;
import com.adesso.commentator.bookstore.application.port.in.usecase.AddToCartUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.ClearCartUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.ModifyCartUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.RemoveFromCartUseCase;
import com.adesso.commentator.bookstore.application.port.out.CartPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CartService implements AddToCartUseCase, RemoveFromCartUseCase, GetCartQuery, ModifyCartUseCase, ClearCartUseCase {
    @Autowired
    private CartPort cartPort;

    @Override
    public void addToCart(BillBookDto book, String user) {
        cartPort.addToCart(book, user);
    }

    @Override
    public void removeFromCart(BillBookDto book, String user) {
        cartPort.removeFromCart(book, user);
    }

    @Override
    public List<BillBookDto> getCart(String user) {
        List<BillBookDto> cart = cartPort.getCart(user);
        if (cart != null) cart.sort(Comparator.comparing(BillBookDto::getId));
        return cart;
    }

    @Override
    public void modifyCart(BillBookDto book, String user) {
        cartPort.removeFromCart(book, user);
        cartPort.addToCart(book, user);
    }

    @Override
    public void clearCart(String user) {
        cartPort.clearCart(user);
    }
}
