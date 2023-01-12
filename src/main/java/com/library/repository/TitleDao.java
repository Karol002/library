package com.library.repository;

import com.library.model.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface TitleDao extends CrudRepository<Title, Long> {
}
