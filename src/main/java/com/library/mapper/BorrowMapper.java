package com.library.mapper;

import com.library.controller.exception.single.CopyNotFoundException;
import com.library.controller.exception.single.ReaderNotFoundException;
import com.library.domain.Borrow;
import com.library.domain.dto.BorrowDto;
import com.library.domain.dto.post.SaveBorrowDto;
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

    public Borrow mapToBorrow(SaveBorrowDto saveBorrowDto) throws CopyNotFoundException, ReaderNotFoundException {
        return new Borrow(
                copyService.getCopy(saveBorrowDto.getCopyId()),
                readerService.getReader(saveBorrowDto.getReaderId())
        );
    }

    public BorrowDto mapToBorrowDto(Borrow borrow) {
        return new BorrowDto(borrow.getId(),
                borrow.getBorrowDate(),
                borrow.getReturnDate(),
                borrow.isClosed(),
                borrow.getCopy().getId(),
                borrow.getReader().getId());
    }

    public List<BorrowDto> mapToBorrowDtoList(final List<Borrow> borrowList) {
        return borrowList.stream()
                .map(this::mapToBorrowDto)
                .toList();
    }
}
