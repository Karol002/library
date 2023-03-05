package com.library.repository;

import com.library.domain.Borrow;
import com.library.domain.Reader;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {

    @Query(nativeQuery = true)
    List<Reader> getAllReaders();

    @Query(nativeQuery = true)
    Optional<Reader> getReader(@Param("id") Long id);

    Reader save(Reader reader);
}
