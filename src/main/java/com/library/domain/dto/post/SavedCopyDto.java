package com.library.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SavedCopyDto {
    private String status;
    private Long titleId;
}
