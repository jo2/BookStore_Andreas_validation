package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.adapter.in.dto.BillDto;
import com.adesso.commentator.bookstore.application.port.in.mapper.MapperImpl;
import com.adesso.commentator.bookstore.application.port.in.query.ReadBooksQuery;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.BillingBook;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MapperImplTest {

    @Mock
    private ReadBooksQuery readBooksQuery;

    @InjectMocks
    private MapperImpl mapper;

    @Test
    void billingBookToDomain() {
        BillBookDto dto = MockData.getMockedBillBookDto();
        Book book = MockData.getMockedBook();
        BillingBook billingBook = new BillingBook(
                book,
                dto.getOrderAmount(),
                dto.getDiscount()
        );

        when(readBooksQuery.readBookById(dto.getId())).thenReturn(book);

        assertThat(mapper.toDomain(dto)).usingRecursiveComparison().ignoringFields("id").isEqualTo(billingBook);
    }

    @Test
    void billToDomain() {
        BillDto dto = MockData.getMockedBillDto();

        when(readBooksQuery.readBookById(dto.getBooks().get(0).getId())).thenReturn(MockData.getMockedBook());

        assertThat(mapper.toDomain(dto)).isNotNull();
    }

}
