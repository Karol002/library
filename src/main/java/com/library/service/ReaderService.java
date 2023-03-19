package com.library.service;

import com.library.controller.exception.single.ReaderHaveBorrowedCopy;
import com.library.controller.exception.single.ReaderNotFoundException;
import com.library.domain.Borrow;
import com.library.domain.Reader;
import com.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final BorrowService borrowService;

    public List<Reader> getAllReaders() {
        return readerRepository.getAllReaders();
    }

    public Reader getReader(Long id) throws ReaderNotFoundException{
        return readerRepository.getReader(id).orElseThrow(ReaderNotFoundException::new);
    }

    public void deleteReader(Long id) throws ReaderNotFoundException,ReaderHaveBorrowedCopy {
        if (readerRepository.existsById(id)) {
            if (!haveOpenBorrows(id)) {
                readerRepository.deleteById(id);
            } else throw new ReaderHaveBorrowedCopy();
        } else throw new ReaderNotFoundException();
    }

    private boolean haveOpenBorrows(Long id) {
        List<Borrow> borrows = borrowService.getAllBorrowsByReaderId(id);
        if (!borrows.isEmpty()) {
            for (Borrow borrow : borrows) {
                if (!borrow.isClosed()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Reader updateReader(final Reader reader) throws ReaderNotFoundException {
        if (readerRepository.existsById(reader.getId())) throw new ReaderNotFoundException();
        return readerRepository.save(reader);
    }

    public void saveReader(final Reader reader) {
        readerRepository.save(reader);
    }
}
