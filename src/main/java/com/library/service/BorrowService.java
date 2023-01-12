package com.library.service;

import com.library.domain.Borrow;
import com.library.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowService {
    private final BorrowRepository borrowRepository;

    public List<Borrow> getBorrows() {
        return borrowRepository.findAll();
    }

    public Borrow getBorrow(Long id) {
        return borrowRepository.findById(id).get();
    }

    public void deleteBorrow(Long id) {
        borrowRepository.deleteById(id);
    }

    public Borrow saveBorrow(final Borrow borrow) {
        return  borrowRepository.save(borrow);
    }
}
