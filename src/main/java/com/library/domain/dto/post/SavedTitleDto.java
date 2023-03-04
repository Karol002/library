package com.library.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SavedTitleDto {
    private String title;
    private String author;
    private LocalDate publicationDate;
}
