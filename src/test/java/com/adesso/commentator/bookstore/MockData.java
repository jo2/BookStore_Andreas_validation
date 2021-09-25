package com.adesso.commentator.bookstore;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.adapter.in.dto.BillDto;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.BillingBook;
import com.adesso.commentator.bookstore.domain.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MockData {

    public static Book getMockedBook() {
        return new Book(1L, "ti", "au", 1.0, 2020, 4);
    }

    public static Book getMockedBook_b() {
        return  new Book(2L, "Hello World", "Programmer", 404, 1970, 42);
    }

    public static Book getMockedBook_c() {
        return new Book(3L, "Error 418", "Teapot", 4.18, 1999, 418);
    }

    public static List<Book> getMockedBooks() {
        return Arrays.asList(
                getMockedBook(),
                getMockedBook_b(),
                getMockedBook_c()
        );
    }

    public static BillingBook getMockedBillingBook() {
        return new BillingBook(getMockedBook(), 2, 10);
    }

    public static BillingBook getMockedBillingBook_b() {
        return new BillingBook(getMockedBook_b(), 1, 0);
    }

    public static BillingBook getMockedBillingBook_c() {
        return new BillingBook(getMockedBook_c(), 5 , 4);
    }

    public static List<BillingBook> getMockedBillingBooks() {
        return Arrays.asList(
                getMockedBillingBook(),
                getMockedBillingBook_b(),
                getMockedBillingBook_c()
        );
    }

    public static Bill getMockedBill() {
        return new Bill(
            Collections.singletonList(getMockedBillingBook())
        );
    }

    public static List<Bill> getMockedBills() {
        List<Bill> bills = new ArrayList<>();
        bills.add(
                new Bill(
                        Collections.singletonList(getMockedBillingBook())
                )
        );
        bills.add(
                new Bill(
                        Arrays.asList(
                                new BillingBook(
                                        getMockedBook_b(),
                                        2,
                                        0
                                ),
                                new BillingBook(
                                        getMockedBook_c(),
                                        1,
                                        10
                                )
                        )
                )
        );
        return bills;
    }

    public static BillBookDto getMockedBillBookDto() {
        BillBookDto dto = new BillBookDto();
        dto.setId(1L);
        dto.setOrderAmount(1);
        dto.setDiscount(0);
        return dto;
    }

    public static List<BillBookDto> getMockedBillBookDtos() {
        List<BillBookDto> dtos = new ArrayList<>();
        dtos.add(getMockedBillBookDto());
        return dtos;
    }

    public static BillDto getMockedBillDto() {
        BillDto dto = new BillDto();
        dto.setBooks(getMockedBillBookDtos());
        return dto;
    }
}
