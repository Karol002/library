package com.library.service;

import com.library.domain.Reader;
import com.library.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;

    public List<Reader> getReaders() {
        return readerRepository.findAll();
    }

    public Reader getReader(Long id) {
        return readerRepository.findById(id).get();
    }

    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }

    public Reader saveReader(final Reader reader) {
        return  readerRepository.save(reader);
    }
}
