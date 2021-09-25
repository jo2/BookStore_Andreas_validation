package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.application.port.in.usecase.BuyBooksUseCase;
import com.adesso.commentator.bookstore.application.port.out.CreateBillPort;
import com.adesso.commentator.bookstore.application.port.out.EditBookPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.BillingBook;
import com.adesso.commentator.bookstore.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@AllArgsConstructor
public class BuyBooksService implements BuyBooksUseCase {

    private final CreateBillPort createBillPort;
    private final EditBookPort editBookPort;
    private final ReadBooksPort readBooksPort;

    @Override
    public Bill buyBooks(@Valid Bill bill) {
        bill.getBooks().forEach(billingBook -> {
            Book b = readBooksPort.readBookById(billingBook.getBookId());
            int newStockAmount = b.getStockAmount() - billingBook.getOrderAmount();
            if (newStockAmount < 0) {
                throw new IllegalArgumentException("not enough books to buy");
            }
            b.setStockAmount(newStockAmount);
            editBookPort.editBook(b);
        });
        bill.setTotalPrice(
                Math.round(
                    bill.getBooks().stream().map(BillingBook::getTotalAmount)
                            .reduce(Double::sum)
                            .orElse(0.0)
                            *100
                )/100.0
                //Makes sure to only have 2 digits for the price
        );
        return createBillPort.createBill(bill);
    }
}
