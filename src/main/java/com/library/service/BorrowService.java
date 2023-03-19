package com.library.service;

import com.library.controller.exception.single.BorrowNotFoundException;
import com.library.controller.exception.single.CopyIsBorrowedException;
import com.library.controller.exception.single.CopyNotFoundException;
import com.library.controller.exception.single.OpenBorrowException;
import com.library.domain.Borrow;
import com.library.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public void deleteBorrow(Long id) throws BorrowNotFoundException, CopyNotFoundException, OpenBorrowException {
        Optional<Borrow> borrow = borrowRepository.getBorrow(id);

        if (borrow.isPresent()) {
            if (copyService.isCopyAvailable(borrow.get().getCopy().getId())) {
                borrowRepository.deleteById(id);
            } else throw new OpenBorrowException();
        } else throw new BorrowNotFoundException();
    }

    @Transactional
    public Borrow returnCopy(final Borrow borrow) {
        copyService.returnCopy(borrow.getCopy());
        borrow.setReturnDate(LocalDate.now());
        borrow.setClosed(true);
        return borrowRepository.save(borrow);
    }

    @Transactional
    public void saveBorrow(final Borrow borrow) throws CopyNotFoundException, CopyIsBorrowedException {
        if (copyService.isCopyAvailable(borrow.getCopy().getId())) {
            copyService.borrowCopy(borrow.getCopy());
            borrowRepository.save(borrow);
        } else throw new CopyIsBorrowedException();
    }
}
