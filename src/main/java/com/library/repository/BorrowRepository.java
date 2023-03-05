package com.library.repository;

import com.library.domain.Borrow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BorrowRepository extends CrudRepository<Borrow, Long> {

    @Query(nativeQuery = true)
    List<Borrow> getAllBorrows();

    @Query(nativeQuery = true)
    Optional<Borrow> getBorrow(@Param("id") Long id);

    Borrow save(Borrow borrow);
}
