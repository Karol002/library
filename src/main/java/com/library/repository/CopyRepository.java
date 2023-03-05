package com.library.repository;

import com.library.domain.Copy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {

    @Query(nativeQuery = true)
    List<Copy> getAllAvailableCopies(@Param("titleId") Long titleId);
    List<Copy> findAll();
    Copy save(Copy copy);
    Optional<Copy> findById(Long id);
}
