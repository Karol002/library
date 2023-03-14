package com.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BorrowDto {
    private Long id;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean closed;
    private Long copyId;
    private Long readerId;
}
