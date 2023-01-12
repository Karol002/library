package com.library.repository;

import com.library.domain.Borrow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BorrowRepository extends CrudRepository<Borrow, Long> {
    List<Borrow> findAll();
    Borrow save(Borrow borrow);
}
