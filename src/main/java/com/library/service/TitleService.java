package com.library.service;

import com.library.controller.exception.single.TitleNotFoundException;
import com.library.domain.Title;
import com.library.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleService {
    private final TitleRepository titleRepository;

    public List<Title> getAllTitles() {
        return titleRepository.getAllTitles();
    }

    public Title getTitle(Long id) throws TitleNotFoundException {
        return titleRepository.getTitle(id).orElseThrow(TitleNotFoundException::new);
    }

    public void deleteTitle(Long id) throws TitleNotFoundException {
        if (titleRepository.getTitle(id).isPresent()) {
            titleRepository.deleteById(id);
        } else throw new TitleNotFoundException();
    }

    public Title updateTitle(final  Title title) throws TitleNotFoundException {
        if (titleRepository.getTitle(title.getId()).isEmpty()) throw new TitleNotFoundException();
        return titleRepository.save(title);
    }

    public void saveTitle(final Title title) { titleRepository.save(title); }
}
