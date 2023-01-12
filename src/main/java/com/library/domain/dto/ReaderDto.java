package com.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ReaderDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate signUpDate;
}
