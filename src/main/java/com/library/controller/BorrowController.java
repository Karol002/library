package com.library.controller;

import com.library.controller.exception.BorrowNotFoundException;
import com.library.controller.exception.CopyNotFoundException;
import com.library.controller.exception.ReaderNotFoundException;
import com.library.domain.Borrow;
import com.library.domain.dto.BorrowDto;
import com.library.mapper.BorrowMapper;
import com.library.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public ResponseEntity<BorrowDto> getBorrow(@PathVariable Long id) throws BorrowNotFoundException {
        return new ResponseEntity<>(borrowMapper.mapToBorrowDto(borrowService.getBorrow(id)), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteBorrow(@PathVariable Long id) {
        borrowService.deleteBorrow(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<BorrowDto> updateBorrow(@RequestBody BorrowDto borrowDto) throws ReaderNotFoundException, CopyNotFoundException {
        Borrow borrow = borrowMapper.mapToBorrow(borrowDto);
        Borrow savedBorrow = borrowService.saveBorrow(borrow);
        return ResponseEntity.ok(borrowMapper.mapToBorrowDto(savedBorrow));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBorrow(@RequestBody BorrowDto borrowDto) throws ReaderNotFoundException, CopyNotFoundException {
        Borrow borrow = borrowMapper.mapToBorrow(borrowDto);
        borrowService.saveBorrow(borrow);
        return ResponseEntity.ok().build();
    }
}
