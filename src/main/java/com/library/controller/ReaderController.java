package com.library.controller;

import com.library.domain.dto.ReaderDto;
import com.library.domain.dto.TitleDto;
import com.library.mapper.ReaderMapper;
import com.library.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/library/readers")
@RequiredArgsConstructor
public class ReaderController {
    private final ReaderService readerService;
    private final ReaderMapper readerMapper;

    @GetMapping
    public List<ReaderDto> getReaders() {
        throw new IllegalArgumentException("Not implementet yet!");
    }

    @GetMapping(value = "{id}")
    public ReaderDto getReader(@PathVariable Long id) {
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
