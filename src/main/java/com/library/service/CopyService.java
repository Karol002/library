package com.library.service;

import com.library.controller.exception.CopyNotFoundException;
import com.library.domain.Copy;
import com.library.repository.CopyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void saveCopy(final Copy copy) { copyRepository.save(copy); }

    public Copy updateCopy(final Copy copy) throws CopyNotFoundException {
        getCopy(copy.getId());
        return  copyRepository.save(copy);
    }

    public void borrowCopy(final Copy copy) {
        copy.setBorrowed(true);
        copyRepository.save(copy);
    }

    public void returnCopy(final Copy copy) {
        copy.setBorrowed(false);
        copyRepository.save(copy);
    }

    public boolean isCopyAvailable(final Long id) throws CopyNotFoundException {
        Copy copy = getCopy(id);
        return !copy.isBorrowed();
    }

    public List<Copy> getAllAvailable(Long titleId) {
        return copyRepository.getAllAvailableCopies(titleId);
    }
}
