package com.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TitleDto {
    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
}
