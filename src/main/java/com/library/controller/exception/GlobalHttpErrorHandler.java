package com.library.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TitleNotFoundException.class)
    public ResponseEntity<Object> handleTitleNotFoundException(TitleNotFoundException exception) {
        return new ResponseEntity<>("Title with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BorrowNotFoundException.class)
    public ResponseEntity<Object> handleBorrowNotFoundException(BorrowNotFoundException exception) {
        return new ResponseEntity<>("Borrow with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CopyNotFoundException.class)
    public ResponseEntity<Object> handleCopyNotFoundException(CopyNotFoundException exception) {
        return new ResponseEntity<>("Copy with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReaderNotFoundException.class)
    public ResponseEntity<Object> handleReaderNotFoundException(ReaderNotFoundException exception) {
        return new ResponseEntity<>("Reader with given id doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CopyIsBorrowedException.class)
    public ResponseEntity<Object> handleCopyIsBorrowedException(CopyIsBorrowedException exception) {
        return new ResponseEntity<>("Copy is borrowed", HttpStatus.IM_USED);
    }

    @ExceptionHandler(OpenBorrowException.class)
    public ResponseEntity<Object> handleOpenBorrowException(CopyIsBorrowedException exception) {
        return new ResponseEntity<>("Copy is not returned, can not delete borrow", HttpStatus.IM_USED);
    }
}
