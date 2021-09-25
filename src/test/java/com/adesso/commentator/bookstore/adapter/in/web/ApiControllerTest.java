package com.adesso.commentator.bookstore.adapter.in.web;

import com.adesso.commentator.bookstore.adapter.in.dto.BillDto;
import com.adesso.commentator.bookstore.application.port.in.mapper.Mapper;
import com.adesso.commentator.bookstore.application.port.in.query.ReadBillsQuery;
import com.adesso.commentator.bookstore.application.port.in.query.ReadBooksQuery;
import com.adesso.commentator.bookstore.application.port.in.usecase.BuyBooksUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.CreateBookUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.DeleteBookUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.EditBookUseCase;
import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ApiControllerTest {

    @Mock
    private ReadBooksQuery readBooksQuery;

    @Mock
    private ReadBillsQuery readBillsQuery;

    @Mock
    private BuyBooksUseCase buyBooksUseCase;

    @Mock
    private CreateBookUseCase createBookUseCase;

    @Mock
    private DeleteBookUseCase deleteBookUseCase;

    @Mock
    private EditBookUseCase editBookUseCase;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ApiController controller;


    @Test
    void getAllBooks() {
        List<Book> books = MockData.getMockedBooks();

        when(readBooksQuery.readAllBooks()).thenReturn(books);

        assertThat(controller.getAllBooks()).isEqualTo(books);
    }

    @Test
    void getBookById() {
        Book book = MockData.getMockedBook();

        when(readBooksQuery.readBookById(book.getId())).thenReturn(book);

        assertThat(controller.getBookById(book.getId())).isEqualTo(book);
    }

    @Test
    void createBook() {
        Book book = MockData.getMockedBook();

        when(createBookUseCase.createBook(book)).thenReturn(book);

        assertThat(controller.createBook(book)).isEqualTo(book);
    }

    @Test
    void editBook() {
        Book book = MockData.getMockedBook();

        when(editBookUseCase.editBook(book)).thenReturn(book);

        assertThat(controller.editBook(book)).isEqualTo(book);
    }

    @Test
    void deleteBook() {
        controller.deleteBook(1);
        verify(deleteBookUseCase).deleteBookById(1);
    }

    @Test
    void buyBooks() {
        BillDto billdto = MockData.getMockedBillDto();
        Bill bill = MockData.getMockedBill();
        Book book = MockData.getMockedBook();

        when(mapper.toDomain(billdto)).thenReturn(bill);

        when(buyBooksUseCase.buyBooks(bill)).thenReturn(bill);

        assertThat(controller.buyBooks(billdto)).isEqualTo(bill);

    }

    @Test
    void getAllBills() {
        List<Bill> bills = MockData.getMockedBills();

        when(readBillsQuery.readAllBills()).thenReturn(bills);

        assertThat(controller.getAllBills()).usingRecursiveComparison().isEqualTo(bills);
    }
}
