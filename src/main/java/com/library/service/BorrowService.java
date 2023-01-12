package com.library.service;

import com.library.domain.Borrow;
import com.library.repository.BorrowDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowService {
    BorrowDao borrowDao;

    public List<Borrow> getBorrows() {
        return borrowDao.findAll();
    }

    public Borrow getBorrow(Long id) {
        return borrowDao.findById(id).get();
    }

    public void deleteBorrow(Long id) {
        borrowDao.deleteById(id);
    }

    public Borrow saveBorrow(final Borrow borrow) {
        return  borrowDao.save(borrow);
    }
}
