package com.jpan.retail.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookRetailExceptionHandler {

    @ExceptionHandler(value = BookRetailException.class)
    public final ResponseEntity<BookRetailException> bookRetailExceptionHandler(BookRetailException bre) {
        return ResponseEntity.status(bre.getErrorCode().getHttpStatus()).body(bre);
    }

    @ExceptionHandler(value = Exception.class)
    public final ResponseEntity<Exception> exceptionHandler(Exception ex) {
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(ex);
    }
}
