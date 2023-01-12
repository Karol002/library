package com.library.repository;

import com.library.domain.Copy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CopyDao extends CrudRepository<Copy, Long> {
    List<Copy> findAll();
}
