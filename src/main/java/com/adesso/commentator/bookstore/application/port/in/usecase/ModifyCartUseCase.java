package com.adesso.commentator.bookstore.application.port.in.usecase;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;

public interface ModifyCartUseCase {

    void modifyCart(BillBookDto book, String user);
}
