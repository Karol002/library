package com.library.controller;

import com.library.controller.exception.single.CopyNotFoundException;
import com.library.controller.exception.single.TitleNotFoundException;
import com.library.domain.Copy;
import com.library.domain.dto.CopyDto;
import com.library.domain.dto.post.SavedCopyDto;
import com.library.mapper.CopyMapper;
import com.library.service.CopyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Copy Controller", description = "Operations related to copies")
@RestController
@RequestMapping("/library/copies")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CopyController {
    private final CopyService copyService;
    private final CopyMapper copyMapper;

    @Operation(summary = "Get all copies")
    @GetMapping
    public ResponseEntity<List<CopyDto>> getAllCopies() {
        List<Copy> copies = copyService.getAllCopies();
        return ResponseEntity.ok(copyMapper.mapToCopyDtoList(copies));
    }

    @Operation(summary = "Get single copy by given id")
    @GetMapping(value = "{id}")
    public ResponseEntity<CopyDto> getCopy(@PathVariable Long id) throws CopyNotFoundException {
        return new ResponseEntity<>(copyMapper.mapToCopyDto(copyService.getCopy(id)), HttpStatus.OK);
    }

    @Operation(summary = "Get available copies for the given title id")
    @GetMapping(value = "/available/{titleId}")
    public ResponseEntity<List<CopyDto>> getAvailableCopy(@PathVariable Long titleId) {
        return new ResponseEntity<>(copyMapper.mapToCopyDtoList(copyService.getAllAvailable(titleId)), HttpStatus.OK);
    }

    @Operation(summary = "Delete copy by given id")
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteCopy(@PathVariable Long id) throws CopyNotFoundException {
        copyService.deleteCopy(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Add new copy by given title id")
    @PostMapping(value = "{titleId}")
    public ResponseEntity<Void> createCopy(@PathVariable Long titleId) throws TitleNotFoundException {
        Copy copy = copyMapper.mapToCopy(new SavedCopyDto(titleId));
        copyService.saveCopy(copy);
        return ResponseEntity.ok().build();
    }
}
