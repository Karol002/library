package com.library.controller;

import com.library.controller.exception.single.TitleNotFoundException;
import com.library.domain.Title;
import com.library.domain.dto.TitleDto;
import com.library.domain.dto.post.SavedTitleDto;
import com.library.mapper.TitleMapper;
import com.library.service.TitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Title Controller", description = "Operations related to titles")
@RestController
@RequestMapping("/library/titles")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TitleController {
    private final TitleService titleService;
    private final TitleMapper titleMapper;

    @Operation(summary = "Get all titles")
    @GetMapping
    public ResponseEntity<List<TitleDto>> getAllTitles() {
        List<Title> titles = titleService.getAllTitles();
        return ResponseEntity.ok(titleMapper.mapToTitleDtoList(titles));
    }

    @Operation(summary = "Get single title by given id")
    @GetMapping(value = "{id}")
    public ResponseEntity<TitleDto> getTitle(@PathVariable Long id) throws TitleNotFoundException {
        return new ResponseEntity<>(titleMapper.mapToTitleDto(titleService.getTitle(id)), HttpStatus.OK);
    }

    @Operation(summary = "Delete title by given id")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteTitle(@PathVariable Long id) throws TitleNotFoundException {
        titleService.deleteTitle(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update title data")
    @PutMapping
    public ResponseEntity<TitleDto> updateTitle(@RequestBody TitleDto titleDto) throws TitleNotFoundException {
        Title title = titleMapper.mapToTitle(titleDto);
        Title savedTitle = titleService.updateTitle(title);
        return ResponseEntity.ok(titleMapper.mapToTitleDto(savedTitle));
    }

    @Operation(summary = "Add new title")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTitle(@RequestBody SavedTitleDto savedTitleDto) {
        Title title = titleMapper.mapToTitle(savedTitleDto);
        titleService.saveTitle(title);
        return ResponseEntity.ok().build();
    }
}