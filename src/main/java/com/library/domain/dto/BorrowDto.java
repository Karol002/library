package com.library.domain.dto;

import com.library.domain.Copy;
import com.library.domain.Reader;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class BorrowDto {
    private Long id;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Long copyId;
    private Long readerId;
}
