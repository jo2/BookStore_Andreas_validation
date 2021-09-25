package com.adesso.commentator.bookstore.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String field;
    private String message;
    private String type;
}
