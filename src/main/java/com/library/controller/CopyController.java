package com.library.controller;

import com.library.domain.dto.CopyDto;
import com.library.mapper.CopyMapper;
import com.library.service.CopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/copies")
@RequiredArgsConstructor
public class CopyController {
    private final CopyService copyService;
    private final CopyMapper copyMapper;

    @GetMapping
    public List<CopyDto> getCopies() {
        throw new IllegalArgumentException("Not implementet yet!");
    }

    @GetMapping(value = "{id}")
    public CopyDto getCopies(@PathVariable Long id) {
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
