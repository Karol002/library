package com.library.reader;

import com.library.domain.Reader;
import com.library.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ReaderTestSuite {
    @Autowired
    private ReaderRepository readerRepository;

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
        List<Reader> readers = readerRepository.findAll();

        //Then
        assertEquals(3, readers.size());

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
}
