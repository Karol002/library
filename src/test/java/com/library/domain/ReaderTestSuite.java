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
public class ReaderTestSuite {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Test
    void testFindReaderById() {
        //Given
        Reader robJohnson = new Reader("Rob", "Johnson", LocalDate.now());
        Reader christianSmith = new Reader("Christian", "Smith", LocalDate.now());
        Reader kimJackson = new Reader( "Kim", "Jackson", LocalDate.now());

        readerRepository.save(robJohnson);
        readerRepository.save(christianSmith);
        readerRepository.save(kimJackson);

        //When
        Long robJohnsonId = robJohnson.getId();
        Long christianSmithId = christianSmith.getId();
        Long kimJacksonId = kimJackson.getId();

        Optional<Reader> testReader1 = readerRepository.findById(robJohnsonId);
        Optional<Reader> testReader2 = readerRepository.findById(christianSmithId);
        Optional<Reader> testReader3 = readerRepository.findById(kimJacksonId);

        //Then
        assertEquals(robJohnsonId, testReader1.get().getId());
        assertEquals(christianSmithId, testReader2.get().getId());
        assertEquals(kimJacksonId, testReader3.get().getId());

        //CleanUp
        readerRepository.deleteAll();
    }

    @Test
    void testFinAllReaders() {
        //Given
        Reader robJohnson = new Reader( "Rob", "Johnson", LocalDate.now());
        Reader christianSmith = new Reader("Christian", "Smith", LocalDate.now());
        Reader kimJackson = new Reader("Kim", "Jackson", LocalDate.now());

        readerRepository.save(robJohnson);
        readerRepository.save(christianSmith);
        readerRepository.save(kimJackson);

        //When
        long readers = readerRepository.count();

        //Then
        assertEquals(3, readers);

        //CleanUp
        readerRepository.deleteAll();
    }

    @Test
    void testDeleteReaderById() {
        //Given
        Reader robJohnson = new Reader("Rob", "Johnson", LocalDate.now());
        Reader christianSmith = new Reader("Christian", "Smith", LocalDate.now());
        Reader kimJackson = new Reader("Kim", "Jackson", LocalDate.now());

        readerRepository.save(robJohnson);
        readerRepository.save(christianSmith);
        readerRepository.save(kimJackson);

        //When
        Long robJohnsonId = robJohnson.getId();
        Long christianSmithId = christianSmith.getId();
        Long kimJacksonId = kimJackson.getId();

        readerRepository.deleteById(christianSmithId);
        readerRepository.deleteById(kimJacksonId);

        List<Reader> readers = readerRepository.findAll();

        //Then
        assertEquals(1, readers.size());
        assertEquals(robJohnsonId, readers.get(0).getId());

        //CleanUp
        readerRepository.deleteAll();
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
        long readersSizeBeforeDelete = readerRepository.count();
        borrowRepository.deleteAll();
        long readersSizeAfterDelete = readerRepository.count();

        //Then
        assertEquals(2, readersSizeBeforeDelete);
        assertEquals(2, readersSizeAfterDelete);

        //CleanUp
        borrowRepository.deleteAll();
        copyRepository.deleteAll();
        readerRepository.deleteAll();
        titleRepository.deleteAll();
    }
}
