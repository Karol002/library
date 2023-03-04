package com.library.mapper;

import com.library.domain.Reader;
import com.library.domain.dto.ReaderDto;
import com.library.domain.dto.post.SavedReaderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderMapper {
    public Reader mapToReader(final ReaderDto readerDto) {
        return new Reader(
                readerDto.getId(),
                readerDto.getFirstName(),
                readerDto.getLastName(),
                readerDto.getSignUpDate()
        );
    }

    public Reader mapToReader(final SavedReaderDto savedReaderDto) {
        return new Reader(
                savedReaderDto.getFirstName(),
                savedReaderDto.getLastName(),
                savedReaderDto.getSignUpDate()
        );
    }

    public ReaderDto mapToReaderDto(final Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getFirstName(),
                reader.getLastName(),
                reader.getSignUpDate()
        );
    }

    public List<ReaderDto> mapToReaderDtoList(final List<Reader> readerList) {
        return readerList.stream()
                .map(this::mapToReaderDto)
                .toList();
    }
}
