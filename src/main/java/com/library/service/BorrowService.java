package com.library.service;

import com.library.controller.exception.single.*;
import com.library.domain.Borrow;
import com.library.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final CopyService copyService;

    public List<Borrow> getAllBorrows() {
        return borrowRepository.getAllBorrows();
    }

    public List<Borrow> getAllBorrowsByReaderId(Long readerId) {
          return borrowRepository.getAllBorrowsByReaderId(readerId);
    }


    public Borrow getBorrow(Long id) throws BorrowNotFoundException {
        return borrowRepository.getBorrow(id).orElseThrow(BorrowNotFoundException::new);
    }

    public void deleteBorrow(Long id) throws BorrowNotFoundException, CopyNotFoundException, OpenBorrowException {
        if (borrowRepository.getBorrow(id).isPresent()) {
            Optional<Borrow> borrow = borrowRepository.getBorrow(id);
            if (copyService.isCopyAvailable(borrow.get().getCopy().getId())) {
                borrowRepository.deleteById(id);
            } else throw new OpenBorrowException();
        } else throw new BorrowNotFoundException();
    }

    public Borrow returnCopy(final Borrow borrow) {
        copyService.returnCopy(borrow.getCopy());
        borrow.setReturnDate(LocalDate.now());
        borrow.setClosed(true);
        return borrowRepository.save(borrow);
    }

    public void saveBorrow(final Borrow borrow) throws CopyNotFoundException, CopyIsBorrowedException {
        if (copyService.isCopyAvailable(borrow.getCopy().getId())) {
            copyService.borrowCopy(borrow.getCopy());
            borrowRepository.save(borrow);
        } else throw new CopyIsBorrowedException();
    }
}
