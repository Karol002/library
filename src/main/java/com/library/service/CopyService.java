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

    public List<Copy> getAllCopies() {
        return copyRepository.getAllCopies();
    }

    public List<Copy> getAllAvailable(Long titleId) {
        return copyRepository.getAllAvailableCopies(titleId);
    }

    public Copy getCopy(Long id) throws CopyNotFoundException{
        return copyRepository.getCopy(id).orElseThrow(CopyNotFoundException::new);
    }

    public void deleteCopy(Long id) throws CopyNotFoundException {
        if (copyRepository.getCopy(id).isPresent()) {
            copyRepository.deleteById(id);
        } else throw new CopyNotFoundException();
    }

    public void saveCopy(final Copy copy) { copyRepository.save(copy); }

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
}
