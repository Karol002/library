package com.library.repository;

import com.library.model.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CopyDao extends CrudRepository<Copy, Long> {
}
