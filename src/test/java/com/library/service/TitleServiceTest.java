package com.library.service;

import com.library.config.Deleter;
import com.library.controller.exception.single.TitleNotFoundException;
import com.library.domain.Copy;
import com.library.domain.Title;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TitleServiceTest {

    @Autowired
    private TitleService titleService;

    @Autowired
    private CopyService copyService;

    @Autowired
    private Deleter deleter;

    @AfterEach
    public void clean() {
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testFindBookById() throws TitleNotFoundException {
        //Given
        Title humanKind = new Title("HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Title greekMyths = new Title("GREEK MYTHS", "JAN LEWIS", LocalDate.of(2010, 11, 16));
        Title chinese = new Title("Chinese", "Michael Jordan", LocalDate.of(2020, 1, 12));

        titleService.saveTitle(humanKind);
        titleService.saveTitle(greekMyths);
        titleService.saveTitle(chinese);

        //When
        Long humanKindId = humanKind.getId();
        Long greekMythsId = greekMyths.getId();
        Long chineseId = chinese.getId();

        Title testBook1 = titleService.getTitle(humanKindId);
        Title testBook2 = titleService.getTitle(greekMythsId);
        Title testBook3 = titleService.getTitle(chineseId);

        //Then
        assertEquals(humanKindId, testBook1.getId());
        assertEquals(greekMythsId, testBook2.getId());
        assertEquals(chineseId, testBook3.getId());
    }

    @Test
    void testFindAllBooks() {
        //Given
        Title humanKind = new Title("HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Title greekMyths = new Title("GREEK MYTHS", "JAN LEWIS", LocalDate.of(2010, 11, 16));
        Title chinese = new Title("Chinese", "Michael Jordan", LocalDate.of(2020, 1, 12));

        titleService.saveTitle(humanKind);
        titleService.saveTitle(greekMyths);
        titleService.saveTitle(chinese);

        //When
        int books = titleService.getAllTitles().size();

        //Then
        assertEquals(3, books);
    }

    @Test
    void testDeleteBookById() throws TitleNotFoundException {
        //Given
        Title humanKind = new Title("HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Title greekMyths = new Title("GREEK MYTHS", "JAN LEWIS", LocalDate.of(2010, 11, 16));
        Title chinese = new Title("Chinese", "Michael Jordan", LocalDate.of(2020, 1, 12));

        titleService.saveTitle(humanKind);
        titleService.saveTitle(greekMyths);
        titleService.saveTitle(chinese);

        //When
        Long humanKindId = humanKind.getId();
        Long greekMythsId = greekMyths.getId();
        Long chineseId = chinese.getId();

        titleService.deleteTitle(chineseId);
        titleService.deleteTitle(greekMythsId);

        List<Title> books = titleService.getAllTitles();

        //Then
        assertEquals(1, books.size());
        assertEquals(humanKindId, books.get(0).getId());
    }

    @Test
    void testCascadeWhenDeleteCopy() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        Copy firstCopy = new Copy(humanKind);
        Copy secondCopy = new Copy(humanKind);

        titleService.saveTitle(humanKind);
        copyService.saveCopy(firstCopy);
        copyService.saveCopy(secondCopy);

        //When
        int titlesSizeBeforeDelete = titleService.getAllTitles().size();
        deleter.deleteFromCopies();
        int titlesSizeAfterDelete = titleService.getAllTitles().size();

        //Then
        assertEquals(1, titlesSizeBeforeDelete);
        assertEquals(1, titlesSizeAfterDelete);
    }

    @Test
    void shouldThrowTitleNotFoundException() {
        //Given
        Title saveTitle = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        titleService.saveTitle(saveTitle);

        Long falseId = saveTitle.getId() + 1;
        Title unSaveTitle = new Title(falseId, "GREEK MYTHS", "JAN LEWIS", LocalDate.of(2010, 11, 16));

        //When & Then
        assertThrows(TitleNotFoundException.class, () -> titleService.getTitle(falseId));
        assertThrows(TitleNotFoundException.class, () -> titleService.deleteTitle(falseId));
        assertThrows(TitleNotFoundException.class, () -> titleService.updateTitle(unSaveTitle));
    }
}
