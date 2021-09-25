package com.adesso.commentator.bookstore.application.port.in.mapper;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.adapter.in.dto.BillDto;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.BillingBook;

public interface Mapper {

    Bill toDomain(BillDto dto);

    BillingBook toDomain(BillBookDto dto);
}
