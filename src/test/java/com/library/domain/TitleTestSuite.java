package com.library.domain;

import com.library.repository.CopyRepository;
import com.library.repository.TitleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TitleTestSuite {

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Test
    void testFindBookById() {
        //Given
        Title humanKind = new Title("HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Title greekMyths = new Title("GREEK MYTHS", "JAN LEWIS", LocalDate.of(2010, 11, 16));
        Title chinese = new Title("Chinese", "Michael Jordan", LocalDate.of(2020, 1, 12));

        titleRepository.save(humanKind);
        titleRepository.save(greekMyths);
        titleRepository.save(chinese);

        //When
        Long humanKindId = humanKind.getId();
        Long greekMythsId = greekMyths.getId();
        Long chineseId = chinese.getId();

        Optional<Title> testBook1 = titleRepository.findById(humanKindId);
        Optional<Title> testBook2 = titleRepository.findById(greekMythsId);
        Optional<Title> testBook3 = titleRepository.findById(chineseId);

        //Then
        assertEquals(humanKindId, testBook1.get().getId());
        assertEquals(greekMythsId, testBook2.get().getId());
        assertEquals(chineseId, testBook3.get().getId());

        //CleanUp
        titleRepository.deleteAll();
    }

    @Test
    void testFindAllBooks() {
        //Given
        Title humanKind = new Title("HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Title greekMyths = new Title("GREEK MYTHS", "JAN LEWIS", LocalDate.of(2010, 11, 16));
        Title chinese = new Title("Chinese", "Michael Jordan", LocalDate.of(2020, 1, 12));

        titleRepository.save(humanKind);
        titleRepository.save(greekMyths);
        titleRepository.save(chinese);

        //When
        long books = titleRepository.count();

        //Then
        assertEquals(3, books);

        //CleanUp
        titleRepository.deleteAll();
    }

    @Test
    void testDeleteBookById() {
        Title humanKind = new Title("HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Title greekMyths = new Title("GREEK MYTHS", "JAN LEWIS", LocalDate.of(2010, 11, 16));
        Title chinese = new Title("Chinese", "Michael Jordan", LocalDate.of(2020, 1, 12));

        titleRepository.save(humanKind);
        titleRepository.save(greekMyths);
        titleRepository.save(chinese);

        //When
        Long humanKindId = humanKind.getId();
        Long greekMythsId = greekMyths.getId();
        Long chineseId = chinese.getId();

        titleRepository.deleteById(chineseId);
        titleRepository.deleteById(greekMythsId);

        List<Title> books = titleRepository.findAll();

        //Then
        assertEquals(1, books.size());
        assertEquals(humanKindId, books.get(0).getId());

        //CleanUp
        titleRepository.deleteAll();
    }

    @Test
    void testCascadeWhenDeleteCopy() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);

        humanKind.getCopies().add(firstCopy);
        humanKind.getCopies().add(secondCopy);

        titleRepository.save(humanKind);
        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);

        //When
        long titlesSizeBeforeDelete = titleRepository.count();
        copyRepository.deleteAll();
        long titlesSizeAfterDelete = titleRepository.count();

        //Then
        assertEquals(1, titlesSizeBeforeDelete);
        assertEquals(1, titlesSizeAfterDelete);

        //CleanUp
        titleRepository.deleteAll();
        copyRepository.deleteAll();
    }
}
