package com.library.controller;

import com.library.controller.exception.*;
import com.library.domain.Borrow;
import com.library.domain.dto.BorrowDto;
import com.library.domain.dto.post.SavedBorrowDto;
import com.library.mapper.BorrowMapper;
import com.library.service.BorrowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Borrow Controller", description = "Operations related to borrows")
@RestController
@RequestMapping("/library/borrows")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BorrowController {
    private final BorrowService borrowService;
    private final BorrowMapper borrowMapper;

    @Operation(summary = "Get all borrows")
    @GetMapping
    public ResponseEntity<List<BorrowDto>> getAllBorrows() {
        List<Borrow> borrows = borrowService.getAllBorrows();
        return ResponseEntity.ok(borrowMapper.mapToBorrowDtoList(borrows));
    }

    @Operation(summary = "Get single borrow by given id")
    @GetMapping(value = "{id}")
    public ResponseEntity<BorrowDto> getBorrow(@PathVariable Long id) throws BorrowNotFoundException {
        return new ResponseEntity<>(borrowMapper.mapToBorrowDto(borrowService.getBorrow(id)), HttpStatus.OK);
    }

    @Operation(summary = "Delete single borrow by given id")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteBorrow(@PathVariable Long id) throws BorrowNotFoundException, CopyNotFoundException, OpenBorrowException {
        borrowService.deleteBorrow(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Return single copy to library by given zborrow id")
    @PutMapping(value = "/return/{id}")
    public ResponseEntity<BorrowDto> returnCopy(@PathVariable Long id) throws BorrowNotFoundException {
        Borrow borrow = borrowService.getBorrow(id);
        Borrow savedBorrow = borrowService.returnCopy(borrow);
        return ResponseEntity.ok(borrowMapper.mapToBorrowDto(savedBorrow));
    }

    @Operation(summary = "Borrow single copy")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBorrow(@RequestBody SavedBorrowDto savedBorrowDto) throws ReaderNotFoundException, CopyNotFoundException, CopyIsBorrowedException {
        Borrow borrow = borrowMapper.mapToBorrow(savedBorrowDto);
        borrowService.saveBorrow(borrow);
        return ResponseEntity.ok().build();
    }
}
