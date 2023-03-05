package com.library.controller;

import com.library.controller.exception.BorrowNotFoundException;
import com.library.controller.exception.CopyIsBorrowed;
import com.library.controller.exception.CopyNotFoundException;
import com.library.controller.exception.ReaderNotFoundException;
import com.library.domain.Borrow;
import com.library.domain.Copy;
import com.library.domain.dto.BorrowDto;
import com.library.domain.dto.post.SavedBorrowDto;
import com.library.mapper.BorrowMapper;
import com.library.mapper.CopyMapper;
import com.library.service.BorrowService;
import com.library.service.CopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/borrows")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BorrowController {
    private final BorrowService borrowService;
    private final BorrowMapper borrowMapper;
    private final CopyService copyService;

    @GetMapping
    public ResponseEntity<List<BorrowDto>> getBorrows() {
        List<Borrow> borrows = borrowService.getBorrows();
        return ResponseEntity.ok(borrowMapper.mapToBorrowDtoList(borrows));
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
    public ResponseEntity<BorrowDto> updateBorrow(@RequestBody BorrowDto borrowDto) throws ReaderNotFoundException, CopyNotFoundException, BorrowNotFoundException {
        Borrow borrow = borrowMapper.mapToBorrow(borrowDto);
        Borrow savedBorrow = borrowService.updateBorrow(borrow);
        return ResponseEntity.ok(borrowMapper.mapToBorrowDto(savedBorrow));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBorrow(@RequestBody SavedBorrowDto savedBorrowDto) throws ReaderNotFoundException, CopyNotFoundException, CopyIsBorrowed {
        if (copyService.isCopyAvailable(savedBorrowDto.getCopyId())) {
            Borrow borrow = borrowMapper.mapToBorrow(savedBorrowDto);
            copyService.borrowCopy(copyService.getCopy(savedBorrowDto.getCopyId()));
            borrowService.borrowBook(borrow);
        } else {
            throw new CopyIsBorrowed();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/return/{id}")
    public ResponseEntity<BorrowDto> returnBook(@PathVariable Long id) throws CopyNotFoundException, BorrowNotFoundException {
        Borrow borrow = borrowService.getBorrow(id);
        Copy copy = copyService.getCopy(borrow.getCopy().getId());
        copyService.returnCopy(copy);
        Borrow savedBorrow = borrowService.returnBook(borrow);
        return ResponseEntity.ok(borrowMapper.mapToBorrowDto(savedBorrow));
    }
}
