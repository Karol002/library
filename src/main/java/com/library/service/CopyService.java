package com.library.service;

import com.library.domain.Copy;
import com.library.repository.CopyDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CopyService {
    CopyDao copyDao;

    public List<Copy> getCopies() {
        return copyDao.findAll();
    }

    public Copy getCopy(Long id) {
        return copyDao.findById(id).get();
    }

    public void deleteCopy(Long id) {
        copyDao.deleteById(id);
    }

    public Copy saveCopy(final Copy copy) {
        return  copyDao.save(copy);
    }
}
