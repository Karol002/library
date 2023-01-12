package com.library.controller;

import com.library.domain.Borrow;
import com.library.domain.dto.BorrowDto;
import com.library.domain.dto.CopyDto;
import com.library.mapper.BorrowMapper;
import com.library.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/borrows")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;
    private final BorrowMapper borrowMapper;

    @GetMapping
    public ResponseEntity<List<BorrowDto>> getBorrows() {
        List<Borrow> borrows = borrowService.getBorrows();
        return ResponseEntity.ok(borrowMapper.mapToBorrowListDto(borrows));
    }

    @GetMapping(value = "{id}")
    public BorrowDto getBorrow(@PathVariable Long id) {
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
