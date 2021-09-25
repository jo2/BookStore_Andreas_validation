package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.application.port.in.query.ReadBillsQuery;
import com.adesso.commentator.bookstore.application.port.out.ReadBillsPort;
import com.adesso.commentator.bookstore.domain.Bill;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ReadBillsService implements ReadBillsQuery {

    private final ReadBillsPort readBillsPort;

    @Override
    public Bill readBillById(long id) {
        return readBillsPort.readBillById(id);
    }

    @Override
    public List<Bill> readAllBills() {
        List<Bill> bills = readBillsPort.readAllBills();
        bills.sort(Comparator.comparing(Bill::getDate));
        return bills;
    }
}
