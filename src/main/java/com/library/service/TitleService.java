package com.library.service;

import com.library.controller.exception.TitleNotFoundException;
import com.library.domain.Title;
import com.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleService {
    private final TitleRepository titleRepository;

    public List<Title> getTitles() {
        return titleRepository.findAll();
    }

    public Title getTitle(Long id) throws TitleNotFoundException {
        return titleRepository.findById(id).orElseThrow(TitleNotFoundException::new);
    }

    public void deleteTitle(Long id) {
        titleRepository.deleteById(id);
    }

    public void saveTitle(final Title title) { titleRepository.save(title); }

    public Title updateTitle(final  Title title) throws TitleNotFoundException {
        getTitle(title.getId());
        return titleRepository.save(title);
    }
}
