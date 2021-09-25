package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.application.port.out.ReadBillsPort;
import com.adesso.commentator.bookstore.domain.Bill;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ReadBillsServiceTest {

    @Mock
    private ReadBillsPort readBillsPort;

    @InjectMocks
    private ReadBillsService readBillsService;

    @Test
    void readBillById() {
        Bill bill = MockData.getMockedBill();

        when(readBillsPort.readBillById(1)).thenReturn(bill);

        assertThat(readBillsService.readBillById(1)).isEqualTo(bill);
    }

    @Test
    void readAllBills() {
        List<Bill> bills = MockData.getMockedBills();

        when(readBillsPort.readAllBills()).thenReturn(bills);

        assertThat(readBillsService.readAllBills()).containsAll(bills);
    }

}
