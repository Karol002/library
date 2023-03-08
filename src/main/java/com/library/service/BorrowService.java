package com.library.service;

import com.library.controller.exception.BorrowNotFoundException;
import com.library.controller.exception.CopyIsBorrowed;
import com.library.controller.exception.CopyNotFoundException;
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
    private final CopyService copyService;

    public List<Borrow> getAllBorrows() {
        return borrowRepository.getAllBorrows();
    }

    public Borrow getBorrow(Long id) throws BorrowNotFoundException {
        return borrowRepository.getBorrow(id).orElseThrow(BorrowNotFoundException::new);
    }

    public void deleteBorrow(Long id) throws BorrowNotFoundException {
        if (borrowRepository.getBorrow(id).isPresent()) {
            borrowRepository.deleteById(id);
        } else throw new BorrowNotFoundException();
    }

    public Borrow returnCopy(final Borrow borrow) {
        copyService.returnCopy(borrow.getCopy());
        borrow.setReturnDate(LocalDate.now());
        return borrowRepository.save(borrow);
    }

    public void saveBorrow(final Borrow borrow) throws CopyNotFoundException, CopyIsBorrowed {
        if (copyService.isCopyAvailable(borrow.getCopy().getId())) {
            copyService.borrowCopy(borrow.getCopy());
            borrowRepository.save(borrow);
        } else throw new CopyIsBorrowed();
    }
}
