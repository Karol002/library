package com.library.service;

import com.library.domain.Title;
import com.library.repository.TitleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleService {
    TitleDao titleDao;

    public List<Title> getTitles() {
        return titleDao.findAll();
    }

    public Title getTitle(Long id) {
        return titleDao.findById(id).get();
    }

    public void deleteTitle(Long id) {
        titleDao.deleteById(id);
    }

    public Title saveTitle(final Title title) {
        return  titleDao.save(title);
    }
}
