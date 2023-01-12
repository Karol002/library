package com.library.controller;

import com.library.domain.dto.ReaderDto;
import com.library.domain.dto.TitleDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/library/readers")
public class ReaderController {

    @GetMapping
    public List<ReaderDto> getReaders() {
        throw new IllegalArgumentException("Not implementet yet!");
    }

    @GetMapping("{id}")
    public ReaderDto getReader(Long id) {
        return new ReaderDto(1L, "Jan", "Pawel", LocalDate.now());
    }

    @DeleteMapping
    public void deleteReader(Long id) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @PutMapping
    public TitleDto updateReader(ReaderDto ReaderDto) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @PostMapping
    public void createReader(ReaderDto ReaderDto) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }
}
