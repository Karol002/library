package com.library.controller;

import com.library.domain.Copy;
import com.library.domain.dto.CopyDto;
import com.library.mapper.CopyMapper;
import com.library.service.CopyService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CopyDto> getCopy(@PathVariable Long id) {
        return new ResponseEntity<>(copyMapper.mapToCopyDto(copyService.getCopy(id)), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public void deleteCopy(Long id) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @PutMapping
    public CopyDto updateCopy(CopyDto CopyDto) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }

    @PostMapping
    public void createCopy(CopyDto copyDto) {
        throw new IllegalArgumentException("Not implementet yet! title");
    }
}
