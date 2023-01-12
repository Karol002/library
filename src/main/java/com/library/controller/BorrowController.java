package com.library.controller;

import com.library.domain.dto.BorrowDto;
import com.library.domain.dto.CopyDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/borrows")
public class BorrowController {

    @GetMapping
    public List<BorrowDto> getBorrows() {
        throw new IllegalArgumentException("Not implementet yet!");
    }

    @GetMapping("{id}")
    public BorrowDto getBorrow(Long id) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @DeleteMapping
    public void deleteBorrow(Long id) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @PutMapping
    public BorrowDto updateBorrow(CopyDto CopyDto) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @PostMapping
    public void createBorrow(BorrowDto borrowDto) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }
}
