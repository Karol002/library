package com.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CopyDto {
    private Long id;
    private String status;
    private Long titleId;
}
