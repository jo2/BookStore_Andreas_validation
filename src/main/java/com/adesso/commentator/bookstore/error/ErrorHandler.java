package com.adesso.commentator.bookstore.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler{

    private final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleExceptions(Exception e, WebRequest request) {
        log.error(e.getMessage());
        e.printStackTrace();

        List<ErrorResponseDto> errors = new ArrayList<>();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (e instanceof EntityWithIdNotFondException) {
            EntityWithIdNotFondException err = (EntityWithIdNotFondException) e;
            errors.add(new ErrorResponseDto("id", err.id+"", err.entityType.getSimpleName()));
            status = HttpStatus.NOT_FOUND;

        } else if (e instanceof IllegalArgumentException) {
            errors.add(new ErrorResponseDto(
                    null,
                    e.getLocalizedMessage(),
                    "IllegalArgument"
                    )
            );

        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            ex.getBindingResult().getAllErrors().stream().map(
                    err -> {
                        if (err instanceof FieldError) {
                            return new ErrorResponseDto(
                                    ((FieldError) err).getField(),
                                    err.getDefaultMessage(), err.getCode()
                            );
                        }
                        log.debug(err.getClass().getSimpleName());
                        return new ErrorResponseDto(err.getObjectName(), err.getDefaultMessage(), err.getCode());
                    }
            ).forEach(errors::add);

        } else {
            errors.add(new ErrorResponseDto(
                    null,
                    e.getLocalizedMessage(),
                    e.getClass().getSimpleName()
            ));
        }
        return new ResponseEntity<>(errors, status);
    }
}
