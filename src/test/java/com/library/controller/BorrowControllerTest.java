package com.library.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.library.config.LocalDateAdapter;
import com.library.controller.exception.single.*;
import com.library.domain.Borrow;
import com.library.domain.Copy;
import com.library.domain.Reader;
import com.library.domain.Title;
import com.library.domain.dto.BorrowDto;
import com.library.domain.dto.post.SaveBorrowDto;
import com.library.mapper.BorrowMapper;
import com.library.service.BorrowService;
import com.library.service.CopyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@WebMvcTest(BorrowController.class)
public class BorrowControllerTest {
    private static final LocalDate TEST_PUBLICATION_DATE = LocalDate.of(2001, 12, 12);
    private static final LocalDate TEST_SIGN_UP_DATE = LocalDate.of(2021, 12, 12);
    private static final LocalDate TEST_RETURN_DATE = LocalDate.of(2023, 12, 12);
    private static final LocalDate TEST_BORROW_DATE = LocalDate.of(2022, 12, 12);
    private static final String TEST_FIRST_NAME = "Test first name";
    private static final String TEST_LAST_NAME = "Test last name";
    private static final String TEST_AUTHOR = "Test author";
    private static final String TEST_TITLE = "Test title";
    private static final boolean TEST_BORROW_STATUS = false;
    private static final Long TEST_READER_ID = 1L;
    private static final Long TEST_TITLE_ID = 1L;
    private static final Long TEST_COPY_ID = 1L;
    private static final Long TEST_ID = 1L;


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowService borrowService;

    @MockBean
    private BorrowMapper borrowMapper;

    @MockBean
    private CopyService copyService;

    @Test
    void shouldFetchBorrows() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_PUBLICATION_DATE);
        Reader reader = new Reader(TEST_READER_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);
        Copy copy = new Copy(title);

        List<Borrow> borrows = new ArrayList<>();
        Borrow borrow = new Borrow(TEST_BORROW_DATE, TEST_RETURN_DATE, TEST_BORROW_STATUS, copy, reader);
        borrows.add(borrow);
        List<BorrowDto> borrowDtos = new ArrayList<>();
        BorrowDto borrowDto = new BorrowDto(TEST_ID, TEST_BORROW_DATE, TEST_RETURN_DATE, TEST_BORROW_STATUS, TEST_COPY_ID, TEST_READER_ID);
        borrowDtos.add(borrowDto);


        when(borrowService.getAllBorrows()).thenReturn(borrows);
        when(borrowMapper.mapToBorrowDtoList(borrows)).thenReturn(borrowDtos);

        //When & Then
        mockMvc.perform(get("/library/borrows").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].readerId", is(1)))
                .andExpect(jsonPath("$[0].borrowDate", is(TEST_BORROW_DATE.toString())))
                .andExpect(jsonPath("$[0].returnDate", is(TEST_RETURN_DATE.toString())));
    }

    @Test
    void shouldFetchBorrowsForSelectedReader() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_PUBLICATION_DATE);
        Reader reader = new Reader(TEST_READER_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);
        Copy copy = new Copy(title);

        List<Borrow> borrows = new ArrayList<>();
        Borrow borrow = new Borrow(TEST_BORROW_DATE, TEST_RETURN_DATE, TEST_BORROW_STATUS, copy, reader);
        borrows.add(borrow);
        List<BorrowDto> borrowDtos = new ArrayList<>();
        BorrowDto borrowDto = new BorrowDto(TEST_ID, TEST_BORROW_DATE, TEST_RETURN_DATE, TEST_BORROW_STATUS, TEST_COPY_ID, TEST_READER_ID);
        borrowDtos.add(borrowDto);


        when(borrowService.getAllBorrowsByReaderId(TEST_READER_ID)).thenReturn(borrows);
        when(borrowMapper.mapToBorrowDtoList(borrows)).thenReturn(borrowDtos);

        //When & Then
        mockMvc.perform(get("/library/borrows/reader/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].copyId", is(1)))
                .andExpect(jsonPath("$[0].readerId", is(1)))
                .andExpect(jsonPath("$[0].borrowDate", is(TEST_BORROW_DATE.toString())))
                .andExpect(jsonPath("$[0].returnDate", is(TEST_RETURN_DATE.toString())));
    }

    @Test
    void shouldFetchBorrow() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_PUBLICATION_DATE);
        Reader reader = new Reader(TEST_READER_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);
        Copy copy = new Copy(title);
        Borrow borrow = new Borrow(TEST_BORROW_DATE, TEST_RETURN_DATE, TEST_BORROW_STATUS, copy, reader);
        BorrowDto borrowDto = new BorrowDto(TEST_ID, TEST_BORROW_DATE, TEST_RETURN_DATE, TEST_BORROW_STATUS , TEST_COPY_ID, TEST_READER_ID);

        when(borrowService.getBorrow(TEST_ID)).thenReturn(borrow);
        when(borrowMapper.mapToBorrowDto(borrow)).thenReturn(borrowDto);

        //When & Then
        mockMvc.perform(get("/library/borrows/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.copyId", is(1)))
                .andExpect(jsonPath("$.readerId", is(1)))
                .andExpect(jsonPath("$.borrowDate", is(TEST_BORROW_DATE.toString())))
                .andExpect(jsonPath("$.returnDate", is(TEST_RETURN_DATE.toString())));
    }

    @Test
    void shouldReturnCopy() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_PUBLICATION_DATE);
        Reader reader = new Reader(TEST_READER_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);
        Copy copy = new Copy(title);
        Borrow borrow = new Borrow(TEST_BORROW_DATE, TEST_RETURN_DATE, TEST_BORROW_STATUS, copy, reader);
        BorrowDto borrowDto = new BorrowDto(TEST_ID, TEST_BORROW_DATE, TEST_RETURN_DATE, TEST_BORROW_STATUS, TEST_COPY_ID, TEST_READER_ID);

        when(borrowService.getBorrow(TEST_ID)).thenReturn(borrow);
        when(borrowMapper.mapToBorrowDto(borrow)).thenReturn(borrowDto);
        when(borrowService.returnCopy(borrow)).thenReturn(borrow);

        //When & Then
        mockMvc.perform(put("/library/borrows/return/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.copyId", is(1)))
                .andExpect(jsonPath("$.readerId", is(1)))
                .andExpect(jsonPath("$.borrowDate", is(TEST_BORROW_DATE.toString())))
                .andExpect(jsonPath("$.returnDate", is(TEST_RETURN_DATE.toString())));
    }

    @Test
    void shouldCreateBorrow() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_PUBLICATION_DATE);
        Reader reader = new Reader(TEST_READER_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);
        Copy copy = new Copy(title);
        Borrow borrow = new Borrow(TEST_BORROW_DATE, TEST_RETURN_DATE, TEST_BORROW_STATUS, copy, reader);
        SaveBorrowDto borrowDto = new SaveBorrowDto(TEST_COPY_ID, TEST_READER_ID);

        when(borrowMapper.mapToBorrow(borrowDto)).thenReturn(borrow);
        when(copyService.isCopyAvailable(TEST_COPY_ID)).thenReturn(true);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(borrowDto);

        //When & Then
        mockMvc.perform(post("/library/borrows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowBorrowNotFoundException() throws Exception {
        //Given
        SaveBorrowDto borrowDto = new SaveBorrowDto(TEST_COPY_ID, TEST_READER_ID);

        when(borrowService.getBorrow(TEST_ID)).thenThrow(BorrowNotFoundException.class);
        doThrow(BorrowNotFoundException.class).when(borrowService).deleteBorrow(TEST_ID);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(borrowDto);

        //When & Then
        mockMvc.perform(put("/library/borrows/return/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/library/borrows/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/library/borrows/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldThrowCopyNotFoundException() throws Exception {
        //Given
        SaveBorrowDto borrowDto = new SaveBorrowDto(TEST_COPY_ID, TEST_READER_ID);

        when(borrowMapper.mapToBorrow(borrowDto)).thenThrow(CopyNotFoundException.class);
        doThrow(BorrowNotFoundException.class).when(borrowService).deleteBorrow(TEST_ID);


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(borrowDto);

        //When & Then
        mockMvc.perform(post("/library/borrows/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/library/borrows/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldThrowReaderNotFoundException() throws Exception {
        //Given
        SaveBorrowDto saveBorrowDto = new SaveBorrowDto(TEST_COPY_ID, TEST_READER_ID);
        when(borrowMapper.mapToBorrow(saveBorrowDto)).thenThrow(ReaderNotFoundException.class);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(saveBorrowDto);

        //When & Then
        mockMvc.perform(post("/library/borrows/").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldThrowCopyIsBorrowedException() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_PUBLICATION_DATE);
        Reader reader = new Reader(TEST_READER_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);
        Copy copy = new Copy(title);
        Borrow borrow = new Borrow(TEST_BORROW_DATE, TEST_RETURN_DATE, TEST_BORROW_STATUS, copy, reader);
        SaveBorrowDto borrowDto = new SaveBorrowDto(TEST_COPY_ID, TEST_READER_ID);

        when(borrowMapper.mapToBorrow(borrowDto)).thenReturn(borrow);
        doThrow(CopyIsBorrowedException.class).when(borrowService).saveBorrow(borrow);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(borrowDto);

        //When & Then
        mockMvc.perform(post("/library/borrows/").contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldThrowOpenBorrowException() throws Exception {
        //Given
        doThrow(OpenBorrowException.class).when(borrowService).deleteBorrow(TEST_ID);

        //When & Then
        mockMvc.perform(delete("/library/borrows/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }
}
