package com.library.domain.dto;

import com.library.domain.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CopyDto {
    private Long id;
    private String status;
    private Long titleId;
}
