package com.library.service;

import com.library.controller.exception.ReaderNotFoundException;
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

    public Reader getReader(Long id) throws ReaderNotFoundException{
        return readerRepository.findById(id).orElseThrow(ReaderNotFoundException::new);
    }

    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }

    public void saveReader(final Reader reader) {
        readerRepository.save(reader);
    }

    public Reader updateReader(final Reader reader) throws ReaderNotFoundException {
        getReader(reader.getId());
        return  readerRepository.save(reader);
    }
}
