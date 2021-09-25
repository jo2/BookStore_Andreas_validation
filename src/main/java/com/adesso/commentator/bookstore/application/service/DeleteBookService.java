package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.application.port.in.usecase.DeleteBookUseCase;
import com.adesso.commentator.bookstore.application.port.out.DeleteBookPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteBookService implements DeleteBookUseCase {

    private final DeleteBookPort deleteBookPort;

    @Override
    public void deleteBookById(long id) {
        deleteBookPort.deleteBookById(id);
    }
}
