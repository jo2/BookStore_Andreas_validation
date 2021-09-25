package com.adesso.commentator.bookstore.adapter.in.dto;

import java.util.List;
import lombok.Data;

@Data
public class BillDto {
    private List<BillBookDto> books;
}
