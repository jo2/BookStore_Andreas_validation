package com.adesso.commentator.bookstore.adapter.out;

import com.adesso.commentator.bookstore.adapter.out.repositories.BillRepository;
import com.adesso.commentator.bookstore.adapter.out.repositories.BillingBookRepository;
import com.adesso.commentator.bookstore.application.port.out.CreateBillPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBillsPort;
import com.adesso.commentator.bookstore.domain.Bill;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component("BillAdapter")
public class BillAdapter implements ReadBillsPort, CreateBillPort {

    private final BillRepository repository;
    private final BillingBookRepository billingBookRepository;

    @Override
    public Bill createBill(Bill bill) {
        Bill b = Mapper.toDomain(repository.save(Mapper.toDto(bill)));
        b.getBooks().forEach(book -> book.setBillId(b.getId()));
        billingBookRepository.saveAll(b.getBooks().stream().map(Mapper::toDto).collect(Collectors.toList()));
        return b;
    }

    @Override
    public Bill readBillById(long billId) {
        return Mapper.toDomain(repository.findById(billId).orElse(null));
    }

    @Override
    public List<Bill> readAllBills() {
        List<Bill> bills = new ArrayList<>();
        repository.findAll().forEach(it -> bills.add(Mapper.toDomain(it)));
        return bills;
    }
}
