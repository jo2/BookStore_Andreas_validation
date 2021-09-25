package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.application.port.out.CreateBillPort;
import com.adesso.commentator.bookstore.application.port.out.EditBookPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BuyBooksServiceTest {

    @Mock
    private CreateBillPort createBillPort;
    @Mock
    private EditBookPort editBookPort;
    @Mock
    private ReadBooksPort readBooksPort;

    @InjectMocks
    BuyBooksService buyBooksService;


    @Test
    void buyBooks_enoughBooks() {
        Book book = MockData.getMockedBook();
        Bill bill = MockData.getMockedBill();

        when(readBooksPort.readBookById(1)).thenReturn(book);

        buyBooksService.buyBooks(bill);

        assertThat(bill.getTotalPrice()).isEqualTo(1.8);
        assertThat(book.getStockAmount()).isEqualTo(2);

        verify(editBookPort).editBook(book);
        verify(createBillPort).createBill(bill);
    }

    @Test
    void buyBooks_notEnoughBooks() {
        Book book = MockData.getMockedBook();
        book.setStockAmount(0);
        Bill bill = MockData.getMockedBill();

        when(readBooksPort.readBookById(1)).thenReturn(book);

        assertThatThrownBy(()->buyBooksService.buyBooks(bill))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContainingAll("not enough");

    }

}
