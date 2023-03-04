package com.library.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SavedReaderDto {
    private String firstName;
    private String lastName;
    private LocalDate signUpDate;
}
