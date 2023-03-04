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
public class CopyTestSuite {
    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Test
    void testFindCopyById() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Copy firstCopy = new Copy("returned", humanKind);
        Copy secondCopy = new Copy( "returned", humanKind);

        humanKind.getCopies().add(firstCopy);
        humanKind.getCopies().add(secondCopy);

        titleRepository.save(humanKind);
        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);

        //When
        Optional<Copy> testCopy1 = copyRepository.findById(firstCopy.getId());
        Optional<Copy> testCopy2 = copyRepository.findById(secondCopy.getId());

        //Then
        assertEquals(firstCopy.getId(), testCopy1.get().getId());
        assertEquals(secondCopy.getId(), testCopy2.get().getId());

        //CleanUp
        copyRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    void testFindAllCopies() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Copy firstCopy = new Copy("returned", humanKind);
        Copy secondCopy = new Copy( "returned", humanKind);

        humanKind.getCopies().add(firstCopy);
        humanKind.getCopies().add(secondCopy);

        titleRepository.save(humanKind);
        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);

        //When
        long copies = copyRepository.count();

        //Then
        assertEquals(2, copies);

        //CleanUp
        copyRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    void testDeleteCopyById() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Copy firstCopy = new Copy("returned", humanKind);
        Copy secondCopy = new Copy( "returned", humanKind);

        humanKind.getCopies().add(firstCopy);
        humanKind.getCopies().add(secondCopy);

        titleRepository.save(humanKind);
        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);

        //When
        copyRepository.deleteById(firstCopy.getId());
        List<Copy> copies = copyRepository.findAll();
        Long secondCopyId = copies.get(0).getId();

        //Then
        assertEquals(1, copies.size());
        assertEquals(secondCopyId, secondCopy.getId());

        //CleanUp
        copyRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    void testCascadeWhenDeleteTitle() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Copy firstCopy = new Copy("returned", humanKind);
        Copy secondCopy = new Copy( "returned", humanKind);

        humanKind.getCopies().add(firstCopy);
        humanKind.getCopies().add(secondCopy);

        titleRepository.save(humanKind);
        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);

        //When
        long copiesSizeBeforeDelete = copyRepository.count();
        titleRepository.deleteById(humanKind.getId());
        long copiesSizeAfterDelete = copyRepository.count();

        //Then
        assertEquals(2, copiesSizeBeforeDelete);
        assertEquals(0, copiesSizeAfterDelete);

        //CleanUp
        titleRepository.deleteAll();
        copyRepository.deleteAll();
    }

    @Test
    void testCascadeWhenRemoveBorrow() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Reader robJohnson = new Reader( "Rob", "Johnson", LocalDate.now());
        Reader christianSmith = new Reader( "Christian", "Smith", LocalDate.now());
        Copy firstCopy = new Copy("returned", humanKind);
        Copy secondCopy = new Copy( "returned", humanKind);
        Borrow firstBorrow = new Borrow(LocalDate.now(), LocalDate.now().plusDays(30), firstCopy, robJohnson);
        Borrow secondBorrow = new Borrow(LocalDate.now(), LocalDate.now().plusDays(30),  secondCopy, christianSmith);

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
        long copiesSizeBeforeDelete = copyRepository.count();
        borrowRepository.deleteAll();
        long copiesSizeAfterDelete = copyRepository.count();

        //Then
        assertEquals(2, copiesSizeBeforeDelete);
        assertEquals(2, copiesSizeAfterDelete);

        //CleanUp
        borrowRepository.deleteAll();
        copyRepository.deleteAll();
        readerRepository.deleteAll();
        titleRepository.deleteAll();
    }
}
