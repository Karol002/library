package com.library.mapper;

import com.library.controller.exception.TitleNotFoundException;
import com.library.domain.Copy;
import com.library.domain.dto.CopyDto;
import com.library.domain.dto.post.SavedCopyDto;
import com.library.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CopyMapper {
    private final TitleService titleService;

    public Copy mapToCopy(final SavedCopyDto savedCopyDto) throws TitleNotFoundException {
        return new Copy(titleService.getTitle(savedCopyDto.getTitleId()));
    }

    public Copy mapToCopy(final CopyDto copyDto) throws TitleNotFoundException {
        return new Copy(
                copyDto.getId(),
                copyDto.isBorrowed(),
                titleService.getTitle(copyDto.getTitleId())
        );
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(
                copy.getId(),
                copy.isBorrowed(),
                copy.getTitle().getId()
        );
    }

    public List<CopyDto> mapToCopyDtoList(final List<Copy> copies) {
        return copies.stream()
                .map(this::mapToCopyDto)
                .toList();
    }
}
