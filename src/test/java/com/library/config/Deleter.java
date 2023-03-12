package com.library.config;

import com.library.repository.BorrowRepository;
import com.library.repository.CopyRepository;
import com.library.repository.ReaderRepository;
import com.library.repository.TitleRepository;
import org.springframework.stereotype.Service;

@Service
public class Deleter {
    private final ReaderRepository readerRepository;
    private final TitleRepository titleRepository;
    private final CopyRepository copyRepository;
    private final BorrowRepository borrowRepository;

    public Deleter(ReaderRepository readerRepository, TitleRepository titleRepository, CopyRepository copyRepository, BorrowRepository borrowRepository) {
        this.readerRepository = readerRepository;
        this.titleRepository = titleRepository;
        this.copyRepository = copyRepository;
        this.borrowRepository = borrowRepository;
    }

    public void deleteFromReaders() {
        readerRepository.deleteAll();
    }

    public void deleteFromTitles() {
        titleRepository.deleteAll();
    }

    public void deleteFromCopies() {
        copyRepository.deleteAll();
    }

    public void deleteFromBorrows() {
        borrowRepository.deleteAll();
    }

    public void deleteAllFromEachEntity() {
        borrowRepository.deleteAll();
        copyRepository.deleteAll();
        titleRepository.deleteAll();
        readerRepository.deleteAll();
    }

}
