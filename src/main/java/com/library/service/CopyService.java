package com.library.service;

import com.library.controller.exception.CopyNotFoundException;
import com.library.domain.Copy;
import com.library.repository.CopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CopyService {
    private final CopyRepository copyRepository;

    public List<Copy> getCopies() {
        return copyRepository.findAll();
    }

    public Copy getCopy(Long id) throws CopyNotFoundException{
        return copyRepository.findById(id).orElseThrow(CopyNotFoundException::new);
    }

    public void deleteCopy(Long id) {
        copyRepository.deleteById(id);
    }

    public Copy saveCopy(final Copy copy) {
        return  copyRepository.save(copy);
    }
}
