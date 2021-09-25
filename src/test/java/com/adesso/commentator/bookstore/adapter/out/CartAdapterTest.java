package com.adesso.commentator.bookstore.adapter.out;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CartAdapterTest {

    @Mock
    private HashMap<String, List<BillBookDto>> carts;

    @InjectMocks
    private CartAdapter cartAdapter;

    @Test
    void addToCart_newUser() {
        String user = "Hello World";
        List<BillBookDto> userBooks = new ArrayList<>();
        BillBookDto book = new BillBookDto();

        when(carts.computeIfAbsent(anyString(), any())).thenReturn(null);
        when(carts.get(user)).thenReturn(userBooks);

        cartAdapter.addToCart(book, user);

        verify(carts).put(any(String.class), any(List.class));
        assertThat(userBooks).containsExactly(book);

    }

    @Test
    void addToCart_knownUser() {
        String user = "Hello World";
        List<BillBookDto> userBooks = new ArrayList<>();
        BillBookDto book = new BillBookDto();

        when(carts.computeIfAbsent(anyString(), any())).thenReturn(userBooks);
        when(carts.get(user)).thenReturn(userBooks);

        cartAdapter.addToCart(book, user);

        assertThat(userBooks).containsExactly(book);
    }

    @Test
    void removeFromCart() {
        String user = "Hello World";
        List<BillBookDto> userBooks = new ArrayList<>();
        BillBookDto book = new BillBookDto(), book2 = new BillBookDto();
        book.setId(1L);
        book2.setId(2L);

        userBooks.add(book);
        userBooks.add(book2);

        BillBookDto bookToRemove = new BillBookDto();
        bookToRemove.setId(1L);

        when(carts.get(user)).thenReturn(userBooks);

        cartAdapter.removeFromCart(bookToRemove, user);

        assertThat(userBooks).containsExactly(book2);
    }

    @Test
    void clearCart() {
        String user = "Hello World";
        cartAdapter.clearCart(user);

        verify(carts).remove(user);
    }

}
