package com.library.controller;

import com.library.controller.exception.CopyNotFoundException;
import com.library.controller.exception.TitleNotFoundException;
import com.library.domain.Copy;
import com.library.domain.dto.CopyDto;
import com.library.domain.dto.post.SavedCopyDto;
import com.library.mapper.CopyMapper;
import com.library.service.CopyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library/copies")
@RequiredArgsConstructor
@CrossOrigin("*")
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

    @GetMapping(value = "/available/{titleId}")
    public ResponseEntity<List<CopyDto>> getAvailableCopy(@PathVariable Long titleId) {
        return new ResponseEntity<>(copyMapper.mapToCopyDtoList(copyService.getAllAvailable(titleId)), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteCopy(@PathVariable Long id) {
        copyService.deleteCopy(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "{titleId}")
    public ResponseEntity<Void> createCopy(@PathVariable Long titleId) throws TitleNotFoundException {
        Copy copy = copyMapper.mapToCopy(new SavedCopyDto(titleId));
        copyService.saveCopy(copy);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<CopyDto> updateCopy(@RequestBody CopyDto copyDto) throws TitleNotFoundException, CopyNotFoundException {
        Copy copy = copyMapper.mapToCopy(copyDto);
        Copy savedCopy = copyService.updateCopy(copy);
        return ResponseEntity.ok(copyMapper.mapToCopyDto(savedCopy));
    }
}
