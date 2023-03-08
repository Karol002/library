package com.library.domain;

import com.library.repository.BorrowRepository;
import com.library.repository.CopyRepository;
import com.library.repository.ReaderRepository;
import com.library.repository.TitleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BorrowTestSuite {

    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private BorrowRepository borrowRepository;

    @Test
    void testFindAllBorrows() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        humanKind.getCopies().add(firstCopy);
        humanKind.getCopies().add(secondCopy);
        robJohnson.getBorrows().add(firstBorrow);
        christianSmith.getBorrows().add(secondBorrow);
        firstCopy.getBorrows().add(firstBorrow);
        secondCopy.getBorrows().add(secondBorrow);

        readerRepository.save(robJohnson);
        readerRepository.save(christianSmith);
        titleRepository.save(humanKind);
        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);
        borrowRepository.save(firstBorrow);
        borrowRepository.save(secondBorrow);

        //When
        int borrows = borrowRepository.getAllBorrows().size();

        //Then
        assertEquals(2, borrows);

        //CleanUp
        borrowRepository.deleteAll();
        copyRepository.deleteAll();
        readerRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    void testFindBorrowById() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        humanKind.getCopies().add(firstCopy);
        humanKind.getCopies().add(secondCopy);
        robJohnson.getBorrows().add(firstBorrow);
        christianSmith.getBorrows().add(secondBorrow);
        firstCopy.getBorrows().add(firstBorrow);
        secondCopy.getBorrows().add(secondBorrow);

        readerRepository.save(robJohnson);
        readerRepository.save(christianSmith);
        titleRepository.save(humanKind);
        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);
        borrowRepository.save(firstBorrow);
        borrowRepository.save(secondBorrow);

        //When
        Optional<Borrow> firstBorrowTest = borrowRepository.getBorrow(firstBorrow.getId());
        Optional<Borrow> secondBorrowTest = borrowRepository.getBorrow(secondBorrow.getId());

        //Then
        assertEquals(firstBorrow.getId(), firstBorrowTest.get().getId());
        assertEquals(secondBorrow.getId(), secondBorrowTest.get().getId());

        //CleanUp
        borrowRepository.deleteAll();
        copyRepository.deleteAll();
        readerRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    void testDeleteBorrowById() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        humanKind.getCopies().add(firstCopy);
        humanKind.getCopies().add(secondCopy);
        robJohnson.getBorrows().add(firstBorrow);
        christianSmith.getBorrows().add(secondBorrow);
        firstCopy.getBorrows().add(firstBorrow);
        secondCopy.getBorrows().add(secondBorrow);

        readerRepository.save(robJohnson);
        readerRepository.save(christianSmith);
        titleRepository.save(humanKind);
        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);
        borrowRepository.save(firstBorrow);
        borrowRepository.save(secondBorrow);

        //When
        borrowRepository.deleteById(secondBorrow.getId());
        List<Borrow> borrows = borrowRepository.getAllBorrows();

        //Then
        assertEquals(1, borrows.size());
        assertEquals(firstBorrow.getId(), borrows.get(0).getId());

        //CleanUp
        borrowRepository.deleteAll();
        copyRepository.deleteAll();
        readerRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    void testCascadeWhenRemoveCopy() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        humanKind.getCopies().add(firstCopy);
        humanKind.getCopies().add(secondCopy);
        robJohnson.getBorrows().add(firstBorrow);
        christianSmith.getBorrows().add(secondBorrow);
        firstCopy.getBorrows().add(firstBorrow);
        secondCopy.getBorrows().add(secondBorrow);

        readerRepository.save(robJohnson);
        readerRepository.save(christianSmith);
        titleRepository.save(humanKind);
        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);
        borrowRepository.save(firstBorrow);
        borrowRepository.save(secondBorrow);

        //When
        long borrowsSizeBeforeDelete = borrowRepository.count();
        copyRepository.deleteAll();
        long borrowsSizeAfterDelete = borrowRepository.count();

        //Then
        assertEquals(2, borrowsSizeBeforeDelete);
        assertEquals(0, borrowsSizeAfterDelete);

        //CleanUp
        borrowRepository.deleteAll();
        copyRepository.deleteAll();
        readerRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    void testCascadeWhenRemoveReader() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson");
        Reader christianSmith = new Reader( "Christian", "Smith");
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);
        Borrow firstBorrow = new Borrow(firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(secondCopy, christianSmith);

        humanKind.getCopies().add(firstCopy);
        humanKind.getCopies().add(secondCopy);
        robJohnson.getBorrows().add(firstBorrow);
        christianSmith.getBorrows().add(secondBorrow);
        firstCopy.getBorrows().add(firstBorrow);
        secondCopy.getBorrows().add(secondBorrow);

        readerRepository.save(robJohnson);
        readerRepository.save(christianSmith);
        titleRepository.save(humanKind);
        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);
        borrowRepository.save(firstBorrow);
        borrowRepository.save(secondBorrow);

        //When
        long borrowsSizeBeforeDelete = borrowRepository.count();
        readerRepository.deleteAll();
        long borrowsSizeAfterDelete = borrowRepository.count();

        //Then
        assertEquals(2, borrowsSizeBeforeDelete);
        assertEquals(0, borrowsSizeAfterDelete);

        //CleanUp
        borrowRepository.deleteAll();
        copyRepository.deleteAll();
        readerRepository.deleteAll();
        titleRepository.deleteAll();
    }
}
