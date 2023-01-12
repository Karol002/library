package com.library.repository;

import com.library.model.Borrow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface BorrowedBookDao extends CrudRepository<Borrow, Long> {
}
