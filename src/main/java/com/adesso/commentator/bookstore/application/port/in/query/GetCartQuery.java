package com.adesso.commentator.bookstore.application.port.in.query;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;

import java.util.List;

public interface GetCartQuery {

    List<BillBookDto> getCart(String id);

}
