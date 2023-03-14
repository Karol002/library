package com.library.service;

import com.library.config.Deleter;
import com.library.controller.exception.single.*;
import com.library.domain.Borrow;
import com.library.domain.Copy;
import com.library.domain.Reader;
import com.library.domain.Title;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ReaderServiceTest {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private Deleter deleter;


    @Test
    void testFindReaderById() throws ReaderNotFoundException {
        //Given
        Reader robJohnson = new Reader("Rob", "Johnson");
        Reader christianSmith = new Reader("Christian", "Smith");
        Reader kimJackson = new Reader( "Kim", "Jackson");

        readerService.saveReader(robJohnson);
        readerService.saveReader(christianSmith);
        readerService.saveReader(kimJackson);

        //When
        Long robJohnsonId = robJohnson.getId();
        Long christianSmithId = christianSmith.getId();
        Long kimJacksonId = kimJackson.getId();

        Reader testReader1 = readerService.getReader(robJohnsonId);
        Reader testReader2 = readerService.getReader(christianSmithId);
        Reader testReader3 = readerService.getReader(kimJacksonId);

        //Then
        assertEquals(robJohnsonId, testReader1.getId());
        assertEquals(christianSmithId, testReader2.getId());
        assertEquals(kimJacksonId, testReader3.getId());

        //CleanUp
        deleter.deleteFromReaders();
    }

    @Test
    void testFindAllReaders() {
        //Given
        Reader robJohnson = new Reader("Rob", "Johnson");
        Reader christianSmith = new Reader("Christian", "Smith");
        Reader kimJackson = new Reader( "Kim", "Jackson");

        readerService.saveReader(robJohnson);
        readerService.saveReader(christianSmith);
        readerService.saveReader(kimJackson);

        //When
        int readers = readerService.getAllReaders().size();

        //Then
        assertEquals(3, readers);

        //CleanUp
        deleter.deleteFromReaders();
    }

    @Test
    void testDeleteReaderById() throws ReaderNotFoundException, ReaderHaveBorrowedCopy {
        //Given
        Reader robJohnson = new Reader("Rob", "Johnson");
        Reader christianSmith = new Reader("Christian", "Smith");
        Reader kimJackson = new Reader( "Kim", "Jackson");

        readerService.saveReader(robJohnson);
        readerService.saveReader(christianSmith);
        readerService.saveReader(kimJackson);

        //When
        Long robJohnsonId = robJohnson.getId();
        Long christianSmithId = christianSmith.getId();
        Long kimJacksonId = kimJackson.getId();

        readerService.deleteReader(christianSmithId);
        readerService.deleteReader(kimJacksonId);

        List<Reader> readers = readerService.getAllReaders();

        //Then
        assertEquals(1, readers.size());
        assertEquals(robJohnsonId, readers.get(0).getId());

        //CleanUp
        deleter.deleteFromReaders();
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
        int readersSizeBeforeDelete = readerService.getAllReaders().size();
        deleter.deleteFromBorrows();
        int readersSizeAfterDelete = readerService.getAllReaders().size();

        //Then
        assertEquals(2, readersSizeBeforeDelete);
        assertEquals(2, readersSizeAfterDelete);

        //CleanUp
        deleter.deleteAllFromEachEntity();
    }
}
