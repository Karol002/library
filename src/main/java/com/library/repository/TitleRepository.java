package com.library.repository;

import com.library.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TitleRepository extends CrudRepository<Title, Long> {
    List<Title> findAll();
    Title save(Title title);
    Optional<Title> findById(Long id);
}
