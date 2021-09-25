package com.adesso.commentator.bookstore.adapter.out.repositories;

import com.adesso.commentator.bookstore.adapter.out.entities.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long> {
}
