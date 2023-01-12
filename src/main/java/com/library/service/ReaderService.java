package com.library.service;

import com.library.domain.Reader;
import com.library.repository.ReaderDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {
    ReaderDao readerDao;

    public List<Reader> getReaders() {
        return readerDao.findAll();
    }

    public Reader getReader(Long id) {
        return readerDao.findById(id).get();
    }

    public void deleteReader(Long id) {
        readerDao.deleteById(id);
    }

    public Reader saveReader(final Reader reader) {
        return  readerDao.save(reader);
    }
}
