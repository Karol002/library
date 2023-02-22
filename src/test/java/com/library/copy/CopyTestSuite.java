package com.library.copy;

import com.library.domain.Copy;
import com.library.domain.Title;
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
public class CopyTestSuite {
    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Test
    void testFindCopyById() {
        //Given
        Title humanKind = new Title( "HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        titleRepository.save(humanKind);

        Copy firstCopy = new Copy( "borrowed", humanKind);
        Copy secondCopy = new Copy( "returned", humanKind);

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
        titleRepository.save(humanKind);

        Copy firstCopy = new Copy("borrowed", humanKind);
        Copy secondCopy = new Copy( "returned", humanKind);

        copyRepository.save(firstCopy);
        copyRepository.save(secondCopy);

        //When
        List<Copy> copies = copyRepository.findAll();

        //Then
        assertEquals(2, copies.size());

        //CleanUp
        copyRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    void testDeleteCopyById() {
        //Given
        Title humanKind = new Title("HumanKind", "Rutger Bregman", LocalDate.of(2000, 12, 12));
        titleRepository.save(humanKind);

        Copy firstCopy = new Copy("borrowed", humanKind);
        Copy secondCopy = new Copy("returned", humanKind);

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
}
