package com.library.controller;

import com.library.controller.exception.TitleNotFoundException;
import com.library.domain.Title;
import com.library.domain.dto.TitleDto;
import com.library.domain.dto.post.SavedTitleDto;
import com.library.mapper.TitleMapper;
import com.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/titles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TitleController {
    private final TitleService titleService;
    private final TitleMapper titleMapper;

    @GetMapping
    public ResponseEntity<List<TitleDto>> getAllTitles() {
        List<Title> titles = titleService.getAllTitles();
        return ResponseEntity.ok(titleMapper.mapToTitleDtoList(titles));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<TitleDto> getTitle(@PathVariable Long id) throws TitleNotFoundException {
        return new ResponseEntity<>(titleMapper.mapToTitleDto(titleService.getTitle(id)), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteTitle(@PathVariable Long id) throws TitleNotFoundException {
        titleService.deleteTitle(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<TitleDto> updateTitle(@RequestBody TitleDto titleDto) throws TitleNotFoundException {
        Title title = titleMapper.mapToTitle(titleDto);
        Title savedTitle = titleService.updateTitle(title);
        return ResponseEntity.ok(titleMapper.mapToTitleDto(savedTitle));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTitle(@RequestBody SavedTitleDto savedTitleDto) {
        Title title = titleMapper.mapToTitle(savedTitleDto);
        titleService.saveTitle(title);
        return ResponseEntity.ok().build();
    }
}