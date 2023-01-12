package com.library.controller;

import com.library.domain.dto.CopyDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/copies")
public class CopyController {

    @GetMapping
    public List<CopyDto> getCopies() {
        throw new IllegalArgumentException("Not implementet yet!");
    }

    @GetMapping("{id}")
    public CopyDto getCopies(Long id) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @DeleteMapping
    public void deleteCopy(Long id) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @PutMapping
    public CopyDto updateCopy(CopyDto CopyDto) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @PostMapping
    public void createCopy(CopyDto copyDto) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }
}
