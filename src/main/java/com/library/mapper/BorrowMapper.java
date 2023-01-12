package com.library.mapper;
import com.library.domain.Borrow;
import com.library.domain.dto.BorrowDto;
import com.library.service.CopyService;
import com.library.service.ReaderService;


import java.util.List;

public class BorrowMapper {
    private ReaderService readerService;
    private CopyService copyService;

    public Borrow mapToBorrow(BorrowDto borrowDto) {
        return new Borrow(
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
                borrow.getReader().getId(),
                borrow.getCopy().getId());
    }

    public List<BorrowDto> mapToBorrowListDto(final List<Borrow> borrowList) {
        return borrowList.stream()
                .map(this::mapToBorrowDto)
                .toList();
    }
}
