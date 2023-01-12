package com.library.controller.exception;

import com.library.controller.exception.TitleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TitleNotFoundException.class)
    public ResponseEntity<Object> handleTitleNotFoundException(TitleNotFoundException exception) {
        return new ResponseEntity<>("Title with given id doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BorrowNotFoundException.class)
    public ResponseEntity<Object> handleBorrowNotFoundException(BorrowNotFoundException exception) {
        return new ResponseEntity<>("Borrow with given id doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
