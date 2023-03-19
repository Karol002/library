package com.library.service;

import com.library.config.Deleter;
import com.library.controller.exception.single.CopyIsBorrowedException;
import com.library.controller.exception.single.CopyNotFoundException;
import com.library.controller.exception.single.TitleNotFoundException;
import com.library.domain.Borrow;
import com.library.domain.Copy;
import com.library.domain.Reader;
import com.library.domain.Title;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CopyServiceTest {

    @Autowired
    private CopyService copyService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private Deleter deleter;

    @AfterEach
    public void clean() {
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testFindAllCopies() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);

        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);

        //When
        int copies = copyService.getAllCopies().size();

        //Then
        assertEquals(2, copies);
    }

    @Test
    void testFindCopyById() throws CopyNotFoundException {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);

        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);

        //When
        Copy testCopy1 = copyService.getCopy(firstCopy.getId());
        Copy testCopy2 = copyService.getCopy(secondCopy.getId());

        //Then
        assertEquals(firstCopy.getId(), testCopy1.getId());
        assertEquals(secondCopy.getId(), testCopy2.getId());
    }

    @Test
    void testDeleteCopyById() throws CopyNotFoundException {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);

        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);

        //When
        copyService.deleteCopy(firstCopy.getId());
        List<Copy> copies = copyService.getAllCopies();
        Long secondCopyId = copies.get(0).getId();

        //Then
        assertEquals(1, copies.size());
        assertEquals(secondCopyId, secondCopy.getId());
    }

    @Test
    void testCascadeWhenDeleteTitle() throws TitleNotFoundException {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);

        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);

        //When
        int copiesSizeBeforeDelete = copyService.getAllCopies().size();
        titleService.deleteTitle(humanKind.getId());
        int copiesSizeAfterDelete = copyService.getAllCopies().size();

        //Then
        assertEquals(2, copiesSizeBeforeDelete);
        assertEquals(0, copiesSizeAfterDelete);
    }

    @Test
    void testCascadeWhenRemoveBorrow() throws CopyNotFoundException, CopyIsBorrowedException {
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
        int copiesSizeBeforeDelete = copyService.getAllCopies().size();
        deleter.deleteFromBorrows();
        int copiesSizeAfterDelete = copyService.getAllCopies().size();

        //Then
        assertEquals(2, copiesSizeBeforeDelete);
        assertEquals(2, copiesSizeAfterDelete);
    }
}
