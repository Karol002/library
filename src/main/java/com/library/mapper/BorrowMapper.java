package com.library.mapper;

import com.library.controller.exception.CopyNotFoundException;
import com.library.controller.exception.ReaderNotFoundException;
import com.library.domain.Borrow;
import com.library.domain.dto.BorrowDto;
import com.library.domain.dto.post.SavedBorrowDto;
import com.library.service.CopyService;
import com.library.service.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowMapper {
    private final ReaderService readerService;
    private final CopyService copyService;

    public Borrow mapToBorrow(SavedBorrowDto savedBorrowDto) throws CopyNotFoundException, ReaderNotFoundException {
        return new Borrow(
                copyService.getCopy(savedBorrowDto.getCopyId()),
                readerService.getReader(savedBorrowDto.getReaderId())
        );
    }

    public Borrow mapToBorrow(BorrowDto borrowDto) throws CopyNotFoundException, ReaderNotFoundException {
        return new Borrow(
                borrowDto.getId(),
                borrowDto.getBorrowDate(),
                borrowDto.getReturnDate(),
                copyService.getCopy(borrowDto.getCopyId()),
                readerService.getReader(borrowDto.getReaderId())
        );
    }

    public BorrowDto mapToBorrowDto(Borrow borrow) {
        return new BorrowDto(borrow.getId(),
                borrow.getBorrowDate(),
                borrow.getReturnDate(),
                borrow.getCopy().getId(),
                borrow.getReader().getId());
    }

    public List<BorrowDto> mapToBorrowDtoList(final List<Borrow> borrowList) {
        return borrowList.stream()
                .map(this::mapToBorrowDto)
                .toList();
    }
}
