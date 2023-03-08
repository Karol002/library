package com.library.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.library.config.LocalDateAdapter;
import com.library.domain.Reader;
import com.library.domain.dto.ReaderDto;
import com.library.domain.dto.post.SavedReaderDto;
import com.library.mapper.ReaderMapper;
import com.library.service.ReaderService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@WebMvcTest(ReaderController.class)
public class ReaderControllerTest {

    private static final LocalDate TEST_SIGN_UP_DATE = LocalDate.of(2022, 12, 12);
    private static final String TEST_FIRST_NAME = "Test first name";
    private static final String TEST_LAST_NAME = "Test last name";
    private static final Long TEST_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReaderService readerService;

    @MockBean
    private ReaderMapper readerMapper;

    @Test
    void shouldFetchReaders() throws Exception {
        //Given
        List<ReaderDto> readerDtos = new ArrayList<>();
        ReaderDto readerDto = new ReaderDto(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);
        readerDtos.add(readerDto);
        List<Reader> readers = new ArrayList<>();
        Reader reader = new Reader(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);
        readers.add(reader);

        when(readerService.getAllReaders()).thenReturn(readers);
        when(readerMapper.mapToReaderDtoList(readers)).thenReturn(readerDtos);

        //When & Then
        mockMvc.perform(get("/library/readers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is(TEST_FIRST_NAME)))
                .andExpect(jsonPath("$[0].lastName", is(TEST_LAST_NAME)))
                .andExpect(jsonPath("$[0].signUpDate", is(TEST_SIGN_UP_DATE.toString())));
    }

    @Test
    void shouldFetchReader() throws Exception {
        //Given
        ReaderDto readerDto = new ReaderDto(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);
        Reader reader = new Reader(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);

        when(readerService.getReader(TEST_ID)).thenReturn(reader);
        when(readerMapper.mapToReaderDto(reader)).thenReturn(readerDto);

        //When & Then
        mockMvc.perform(get("/library/readers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(TEST_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(TEST_LAST_NAME)))
                .andExpect(jsonPath("$.signUpDate", is(TEST_SIGN_UP_DATE.toString())));
    }

    @Test
    void shouldUpdateReader() throws Exception {
        //Given
        ReaderDto readerDto = new ReaderDto(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);
        Reader reader = new Reader(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);

        when(readerMapper.mapToReader(readerDto)).thenReturn(reader);
        when(readerMapper.mapToReaderDto(reader)).thenReturn(readerDto);
        when(readerService.updateReader(reader)).thenReturn(reader);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(readerDto);

        //When & Then
        mockMvc.perform(put("/library/readers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(TEST_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(TEST_LAST_NAME)))
                .andExpect(jsonPath("$.signUpDate", is(TEST_SIGN_UP_DATE.toString())));
    }

    @Test
    void shouldCreateReader() throws Exception {
        //Given
        SavedReaderDto readerDto = new SavedReaderDto(TEST_FIRST_NAME, TEST_LAST_NAME);
        Reader reader = new Reader(TEST_ID, TEST_FIRST_NAME, TEST_LAST_NAME, TEST_SIGN_UP_DATE);

        when(readerMapper.mapToReader(readerDto)).thenReturn(reader);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(readerDto);

        //When & Then
        mockMvc.perform(post("/library/readers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteReader() throws Exception {
        //Given & When & Then
        mockMvc.perform(delete("/library/readers/1"))
                .andExpect(status().isOk());
    }
}
