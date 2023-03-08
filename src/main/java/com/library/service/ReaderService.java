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

    public List<Reader> getAllReaders() {
        return readerRepository.getAllReaders();
    }

    public Reader getReader(Long id) throws ReaderNotFoundException{
        return readerRepository.getReader(id).orElseThrow(ReaderNotFoundException::new);
    }

    public void deleteReader(Long id) throws ReaderNotFoundException {
        if (readerRepository.getReader(id).isPresent()) {
            readerRepository.deleteById(id);
        } else throw new ReaderNotFoundException();
    }

    public Reader updateReader(final Reader reader) throws ReaderNotFoundException {
        if (readerRepository.getReader(reader.getId()).isEmpty()) throw new ReaderNotFoundException();
        return readerRepository.save(reader);
    }

    public void saveReader(final Reader reader) {
        readerRepository.save(reader);
    }

}
