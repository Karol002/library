package com.library.mapper;

import com.library.domain.Copy;
import com.library.domain.dto.CopyDto;
import com.library.repository.TitleDao;
import com.library.service.TitleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyMapper {
    private TitleService titleService;

    public CopyMapper(TitleService titleService) {
        this.titleService = titleService;
    }

    public Copy mapToCopy(final CopyDto copyDto) {
        return new Copy(
                copyDto.getStatus(),
                titleService.getTitle(copyDto.getTitleId())
        );
    }

    public CopyDto mapToCopyDto(final Copy copy) {
        return new CopyDto(
                copy.getId(),
                copy.getStatus(),
                copy.getTitle().getId()
        );
    }

    public List<CopyDto> mapToCopyDtoList(final List<Copy> copies) {
        return copies.stream()
                .map(this::mapToCopyDto)
                .toList();
    }
}
