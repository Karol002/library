package com.library.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SavedBorrowDto {
    private Long copyId;
    private Long readerId;
}
