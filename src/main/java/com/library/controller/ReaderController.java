package com.library.controller;

import com.library.controller.exception.ReaderNotFoundException;
import com.library.domain.Reader;
import com.library.domain.dto.ReaderDto;
import com.library.domain.dto.post.SavedReaderDto;
import com.library.mapper.ReaderMapper;
import com.library.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/readers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReaderController {
    private final ReaderService readerService;
    private final ReaderMapper readerMapper;

    @GetMapping
    public ResponseEntity<List<ReaderDto>> getReaders() {
        List<Reader> readers = readerService.getReaders();
        return ResponseEntity.ok(readerMapper.mapToReaderDtoList(readers));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ReaderDto> getReader(@PathVariable Long id) throws ReaderNotFoundException {
        return new ResponseEntity<>(readerMapper.mapToReaderDto(readerService.getReader(id)), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        readerService.deleteReader(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<ReaderDto> updateReader(@RequestBody ReaderDto readerDto) throws ReaderNotFoundException {
        Reader reader = readerMapper.mapToReader(readerDto);
        Reader savedReader = readerService.updateReader(reader);
        return ResponseEntity.ok(readerMapper.mapToReaderDto(savedReader));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReader(@RequestBody SavedReaderDto savedReaderDto) {
        Reader reader = readerMapper.mapToReader(savedReaderDto);
        readerService.saveReader(reader);
        return ResponseEntity.ok().build();
    }
}
