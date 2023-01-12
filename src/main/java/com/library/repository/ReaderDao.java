package com.library.repository;

import com.library.model.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface ReaderDao extends CrudRepository<Reader, Long> {
}
