package com.library.repository;

import com.library.domain.Title;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TitleRepository extends CrudRepository<Title, Long> {

    @Query(nativeQuery = true)
    List<Title> getAllTitles();

    @Query(nativeQuery = true)
    Optional<Title> getTitle(@Param("id") Long id);

    Title save(Title title);
}
