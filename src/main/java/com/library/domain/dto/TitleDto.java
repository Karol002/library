package com.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TitleDto {
    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
}
