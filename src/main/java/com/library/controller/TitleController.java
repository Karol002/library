package com.library.controller;

import com.library.domain.dto.TitleDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/titles")
public class TitleController {

    @GetMapping
    public List<TitleDto> getTitles() {
        throw new IllegalArgumentException("Not implementet yet!");
    }

    @GetMapping("{id}")
    public TitleDto getTitle(Long id) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @DeleteMapping
    public void deleteTitle(Long id) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @PutMapping
    public TitleDto updateTitle(TitleDto titleDto) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @PostMapping
    public void createTitle(TitleDto titleDto) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }
}