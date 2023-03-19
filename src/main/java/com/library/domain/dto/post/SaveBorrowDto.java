package com.library.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SaveBorrowDto {
    private Long copyId;
    private Long readerId;
}
