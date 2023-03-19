package com.library.controller;

import com.library.controller.exception.single.ReaderHaveBorrowedCopy;
import com.library.controller.exception.single.ReaderNotFoundException;
import com.library.domain.Reader;
import com.library.domain.dto.ReaderDto;
import com.library.domain.dto.post.SaveReaderDto;
import com.library.mapper.ReaderMapper;
import com.library.service.ReaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reader Controller", description = "Operations related to readers")
@RestController
@RequestMapping("/library/readers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReaderController {
    private final ReaderService readerService;
    private final ReaderMapper readerMapper;

    @Operation(summary = "Get all readers")
    @GetMapping
    public ResponseEntity<List<ReaderDto>> getAllReaders() {
        List<Reader> readers = readerService.getAllReaders();
        return ResponseEntity.ok(readerMapper.mapToReaderDtoList(readers));
    }

    @Operation(summary = "Get single reader by given id")
    @GetMapping(value = "{id}")
    public ResponseEntity<ReaderDto> getReader(@PathVariable Long id) throws ReaderNotFoundException {
        return new ResponseEntity<>(readerMapper.mapToReaderDto(readerService.getReader(id)), HttpStatus.OK);
    }

    @Operation(summary = "Delete reader by given id")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) throws ReaderNotFoundException, ReaderHaveBorrowedCopy {
        readerService.deleteReader(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update reader data")
    @PutMapping
    public ResponseEntity<ReaderDto> updateReader(@RequestBody ReaderDto readerDto) throws ReaderNotFoundException {
        Reader reader = readerMapper.mapToReader(readerDto);
        Reader savedReader = readerService.updateReader(reader);
        return ResponseEntity.ok(readerMapper.mapToReaderDto(savedReader));
    }

    @Operation(summary = "Add new reader")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createReader(@RequestBody SaveReaderDto saveReaderDto) {
        Reader reader = readerMapper.mapToReader(saveReaderDto);
        readerService.saveReader(reader);
        return ResponseEntity.ok().build();
    }
}
