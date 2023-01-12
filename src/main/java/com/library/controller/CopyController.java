package com.library.controller;

import com.library.controller.exception.CopyNotFoundException;
import com.library.controller.exception.TitleNotFoundException;
import com.library.domain.Copy;
import com.library.domain.dto.CopyDto;
import com.library.mapper.CopyMapper;
import com.library.service.CopyService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/copies")
@RequiredArgsConstructor
public class CopyController {
    private final CopyService copyService;
    private final CopyMapper copyMapper;

    @GetMapping
    public ResponseEntity<List<CopyDto>> getCopies() {
        List<Copy> copies = copyService.getCopies();
        return ResponseEntity.ok(copyMapper.mapToCopyDtoList(copies));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CopyDto> getCopy(@PathVariable Long id) throws CopyNotFoundException {
        return new ResponseEntity<>(copyMapper.mapToCopyDto(copyService.getCopy(id)), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteCopy(@PathVariable Long id) {
        copyService.deleteCopy(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<CopyDto> updateCopy(@RequestBody CopyDto copyDto) throws TitleNotFoundException {
        Copy copy = copyMapper.mapToCopy(copyDto);
        Copy savedCopy = copyService.saveCopy(copy);
        return ResponseEntity.ok(copyMapper.mapToCopyDto(savedCopy));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCopy(@RequestBody CopyDto copyDto) throws TitleNotFoundException {
        Copy copy = copyMapper.mapToCopy(copyDto);
        copyService.saveCopy(copy);
        return ResponseEntity.ok().build();
    }
}
