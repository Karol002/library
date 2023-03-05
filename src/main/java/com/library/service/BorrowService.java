package com.library.service;

import com.library.controller.exception.BorrowNotFoundException;
import com.library.domain.Borrow;
import com.library.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;

    public List<Borrow> getBorrows() {
        return borrowRepository.getAllBorrows();
    }

    public Borrow getBorrow(Long id) throws BorrowNotFoundException {
        return borrowRepository.getBorrow(id).orElseThrow(BorrowNotFoundException::new);
    }

    public void deleteBorrow(Long id) {
        borrowRepository.deleteById(id);
    }

    public void borrowBook(final Borrow borrow) { borrowRepository.save(borrow); }
    
    public Borrow updateBorrow(final Borrow borrow) throws BorrowNotFoundException {
        getBorrow(borrow.getId());
        return borrowRepository.save(borrow);
    }

    public Borrow returnBook(final Borrow borrow) throws BorrowNotFoundException {
        getBorrow(borrow.getId());
        borrow.setReturnDate(LocalDate.now());
        return borrowRepository.save(borrow);
    }
}
