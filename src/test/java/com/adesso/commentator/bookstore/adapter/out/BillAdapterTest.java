package com.adesso.commentator.bookstore.adapter.out;

import com.adesso.commentator.bookstore.adapter.out.repositories.BillRepository;
import com.adesso.commentator.bookstore.adapter.out.repositories.BillingBookRepository;
import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.BillingBook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillAdapterTest {

    @Mock
    private BillRepository repository;

    @Mock
    private BillingBookRepository billingBookRepository;

    @InjectMocks
    private BillAdapter billAdapter;


    @Test
    void createBill() {
        List<BillingBook> books = Collections.singletonList(MockData.getMockedBillingBook());
        Bill bill = new Bill(books);

        when(repository.save(any())).thenReturn(Mapper.toDto(bill));
        when(billingBookRepository.saveAll(any())).thenReturn(null);

        assertThat(billAdapter.createBill(bill)).usingRecursiveComparison().ignoringFieldsMatchingRegexes("books.*\\.id").isEqualTo(bill);

        verify(billingBookRepository).saveAll(anyCollection());
    }

    @Test
    void readBillById() {
        Bill bill = MockData.getMockedBill();
        com.adesso.commentator.bookstore.adapter.out.entities.Bill dtoBill = Mapper.toDto(bill);

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(dtoBill));

        assertThat(billAdapter.readBillById(1L)).usingRecursiveComparison().ignoringFieldsMatchingRegexes("books.*\\.id").isEqualTo(bill);
    }

    @Test
    void readAllBills() {
        List<Bill> bills = MockData.getMockedBills();
        List<com.adesso.commentator.bookstore.adapter.out.entities.Bill> dtoBills = bills.stream().map(Mapper::toDto).collect(Collectors.toList());

        when(repository.findAll()).thenReturn(dtoBills);

        assertThat(billAdapter.readAllBills()).usingRecursiveComparison().ignoringFieldsMatchingRegexes("books.*\\.id").isEqualTo(bills);
    }
}
