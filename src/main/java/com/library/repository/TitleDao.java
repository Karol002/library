package com.library.repository;

import com.library.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface TitleDao extends CrudRepository<Title, Long> {
    List<Title> findAll();
}
