package com.library.repository;

import com.library.model.BorrowedBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BorrowedBookDao extends CrudRepository<BorrowedBook, Long> {
}
