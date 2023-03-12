package com.library.service;

import com.library.config.Deleter;
import com.library.controller.exception.BorrowNotFoundException;
import com.library.controller.exception.CopyIsBorrowed;
import com.library.controller.exception.CopyNotFoundException;
import com.library.domain.Borrow;
import com.library.domain.Copy;
import com.library.domain.Reader;
import com.library.domain.Title;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BorrowServiceTest {

    @Autowired
    private TitleService titleService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private Deleter deleter;

    @Test
    void testFindAllBorrows() throws CopyNotFoundException, CopyIsBorrowed {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        readerService.saveReader(robJohnson);
        readerService.saveReader(christianSmith);
        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);
        borrowService.saveBorrow(firstBorrow);
        borrowService.saveBorrow(secondBorrow);

        //When
        int borrows = borrowService.getAllBorrows().size();

        //Then
        assertEquals(2, borrows);

        //CleanUp
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testFindBorrowById() throws CopyNotFoundException, CopyIsBorrowed, BorrowNotFoundException {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        readerService.saveReader(robJohnson);
        readerService.saveReader(christianSmith);
        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);
        borrowService.saveBorrow(firstBorrow);
        borrowService.saveBorrow(secondBorrow);

        //When
        Borrow firstBorrowTest = borrowService.getBorrow(firstBorrow.getId());
        Borrow secondBorrowTest = borrowService.getBorrow(secondBorrow.getId());

        //Then
        assertEquals(firstBorrow.getId(), firstBorrowTest.getId());
        assertEquals(secondBorrow.getId(), secondBorrowTest.getId());

        //CleanUp
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testDeleteBorrowById() throws CopyNotFoundException, CopyIsBorrowed, BorrowNotFoundException {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        readerService.saveReader(robJohnson);
        readerService.saveReader(christianSmith);
        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);
        borrowService.saveBorrow(firstBorrow);
        borrowService.saveBorrow(secondBorrow);

        //When
        borrowService.deleteBorrow(secondBorrow.getId());
        List<Borrow> borrows = borrowService.getAllBorrows();

        //Then
        assertEquals(1, borrows.size());
        assertEquals(firstBorrow.getId(), borrows.get(0).getId());

        //CleanUp
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testCascadeWhenRemoveCopy() throws CopyNotFoundException, CopyIsBorrowed {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        readerService.saveReader(robJohnson);
        readerService.saveReader(christianSmith);
        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);
        borrowService.saveBorrow(firstBorrow);
        borrowService.saveBorrow(secondBorrow);

        //When
        int borrowsSizeBeforeDelete = borrowService.getAllBorrows().size();
        deleter.deleteFromCopies();
        int borrowsSizeAfterDelete = borrowService.getAllBorrows().size();

        //Then
        assertEquals(2, borrowsSizeBeforeDelete);
        assertEquals(0, borrowsSizeAfterDelete);

        //CleanUp
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testCascadeWhenRemoveReader() throws CopyNotFoundException, CopyIsBorrowed {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        readerService.saveReader(robJohnson);
        readerService.saveReader(christianSmith);
        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);
        borrowService.saveBorrow(firstBorrow);
        borrowService.saveBorrow(secondBorrow);

        //When
        int borrowsSizeBeforeDelete = borrowService.getAllBorrows().size();
        deleter.deleteFromReaders();
        int borrowsSizeAfterDelete = borrowService.getAllBorrows().size();

        //Then
        assertEquals(2, borrowsSizeBeforeDelete);
        assertEquals(0, borrowsSizeAfterDelete);

        //CleanUp
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testBorrowCopy() throws CopyNotFoundException, CopyIsBorrowed {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);

        readerService.saveReader(robJohnson);
        readerService.saveReader(christianSmith);
        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);

        //When
        boolean firstCopyIsNotBorrowed = copyService.isCopyAvailable(firstCopy.getId());
        boolean secondCopyIsNotBorrowed = copyService.isCopyAvailable(secondCopy.getId());

        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);
        borrowService.saveBorrow(firstBorrow);
        borrowService.saveBorrow(secondBorrow);

        boolean firstCopyBorrowed = copyService.isCopyAvailable(firstCopy.getId());
        boolean secondCopyBorrowed = copyService.isCopyAvailable(secondCopy.getId());

        //Then
        assertTrue(firstCopyIsNotBorrowed);
        assertTrue(secondCopyIsNotBorrowed);
        assertFalse(firstCopyBorrowed);
        assertFalse(secondCopyBorrowed);

        //CleanUp
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testReturnCopy() throws CopyNotFoundException, CopyIsBorrowed {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        readerService.saveReader(robJohnson);
        readerService.saveReader(christianSmith);
        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);
        borrowService.saveBorrow(firstBorrow);
        borrowService.saveBorrow(secondBorrow);

        //When
        boolean firstCopyIsNotBorrowed = copyService.isCopyAvailable(firstCopy.getId());
        boolean secondCopyIsNotBorrowed = copyService.isCopyAvailable(secondCopy.getId());

        borrowService.returnCopy(firstBorrow);
        borrowService.returnCopy(secondBorrow);

        LocalDate firstBorrowReturnDateDate = firstBorrow.getReturnDate();
        LocalDate secondBorrowReturnDateDate = secondBorrow.getReturnDate();
        boolean firstCopyBorrowed = copyService.isCopyAvailable(firstCopy.getId());
        boolean secondCopyBorrowed = copyService.isCopyAvailable(secondCopy.getId());

        //Then
        assertEquals(LocalDate.now(), firstBorrowReturnDateDate);
        assertEquals(LocalDate.now(), secondBorrowReturnDateDate);
        assertFalse(firstCopyIsNotBorrowed);
        assertFalse(secondCopyIsNotBorrowed);
        assertTrue(firstCopyBorrowed);
        assertTrue(secondCopyBorrowed);

        //CleanUp
        deleter.deleteAllFromEachEntity();
    }
}
