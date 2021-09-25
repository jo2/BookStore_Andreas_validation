package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.application.port.out.CartPort;
import com.adesso.commentator.bookstore.application.port.out.CreateBillPort;
import com.adesso.commentator.bookstore.application.port.out.EditBookPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartPort cartPort;

    @Mock
    private List<BillBookDto> mockedCart;

    @InjectMocks
    private CartService cartService;

    @Test
    void addToCart() {
        BillBookDto dto = MockData.getMockedBillBookDto();
        String user = "Hello World";

        cartService.addToCart(dto, user);

        verify(cartPort).addToCart(dto, user);
    }

    @Test
    void removeFromCart() {
        BillBookDto dto = MockData.getMockedBillBookDto();
        String user = "Hello World";

        cartService.removeFromCart(dto, user);

        verify(cartPort).removeFromCart(dto, user);
    }

    @Test
    void getCart_existing() {
        String user = "Hello World";

        when(cartPort.getCart(user)).thenReturn(mockedCart);

        assertThat(cartService.getCart(user)).isEqualTo(mockedCart);
        verify(mockedCart).sort(any());
    }

    @Test
    void getCart_notExisting() {
        String user = "Hello World";

        when(cartPort.getCart(user)).thenReturn(null);

        assertThat(cartService.getCart(user)).isNull();
    }

    @Test
    void modifyCart() {
        BillBookDto book = MockData.getMockedBillBookDto();
        String user = "Hello World";

        cartService.modifyCart(book, user);

        verify(cartPort).removeFromCart(book, user);
        verify(cartPort).addToCart(book, user);
    }

    @Test
    void clearCart() {
        String user = "Hello World";

        cartService.clearCart(user);

        verify(cartPort).clearCart(user);
    }
}
