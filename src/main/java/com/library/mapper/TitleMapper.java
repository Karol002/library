package com.library.mapper;

import com.library.domain.Title;
import com.library.domain.dto.TitleDto;
import com.library.domain.dto.post.SaveTitleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleMapper {

    public Title mapToTitle(final SaveTitleDto saveTitleDto) {
        return new Title(
                saveTitleDto.getAuthor(),
                saveTitleDto.getTitle(),
                saveTitleDto.getPublicationDate()
        );
    }

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(
                titleDto.getId(),
                titleDto.getAuthor(),
                titleDto.getTitle(),
                titleDto.getPublicationDate()
        );
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(
                title.getId(),
                title.getAuthor(),
                title.getTitle(),
                title.getPublicationDate()
        );
    }

    public List<TitleDto> mapToTitleDtoList(final List<Title> titleList) {
        return titleList.stream()
                .map(this::mapToTitleDto)
                .toList();
    }
}
