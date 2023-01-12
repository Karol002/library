package com.library.repository;

import com.library.domain.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ReaderRepository extends CrudRepository<Reader, Long> {
    List<Reader> findAll();
    Reader save(Reader reader);
}
