package com.library.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.library.config.LocalDateAdapter;
import com.library.domain.Title;
import com.library.domain.dto.TitleDto;
import com.library.domain.dto.post.SaveTitleDto;
import com.library.mapper.TitleMapper;
import com.library.service.TitleService;
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
@WebMvcTest(TitleController.class)
public class TitleControllerTest {

    private static final LocalDate TEST_DATE = LocalDate.of(2022, 12, 12);
    private static final String TEST_TITLE = "Test title";
    private static final String TEST_AUTHOR = "Test author";
    private static final Long TEST_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TitleService titleService;

    @MockBean
    private TitleMapper titleMapper;

    @Test
    void shouldFetchTitles() throws Exception {
        //Given
        List<TitleDto> titleDtos = new ArrayList<>();
        TitleDto titleDto = new TitleDto(TEST_ID, TEST_TITLE, TEST_AUTHOR, TEST_DATE);
        titleDtos.add(titleDto);
        List<Title> titles = new ArrayList<>();
        Title title = new Title(TEST_TITLE, TEST_AUTHOR, TEST_DATE);
        titles.add(title);

        when(titleService.getAllTitles()).thenReturn(titles);
        when(titleMapper.mapToTitleDtoList(titles)).thenReturn(titleDtos);

        //When & Then
        mockMvc.perform(get("/library/titles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is(TEST_TITLE)))
                .andExpect(jsonPath("$[0].author", is(TEST_AUTHOR)))
                .andExpect(jsonPath("$[0].publicationDate", is(TEST_DATE.toString())));
    }

    @Test
    void shouldFetchTitle() throws Exception {
        //Given
        TitleDto titleDto = new TitleDto(TEST_ID, TEST_TITLE, TEST_AUTHOR, TEST_DATE);
        Title title = new Title(TEST_TITLE, TEST_AUTHOR, TEST_DATE);

        when(titleService.getTitle(TEST_ID)).thenReturn(title);
        when(titleMapper.mapToTitleDto(title)).thenReturn(titleDto);

        //When & Then
        mockMvc.perform(get("/library/titles/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(TEST_TITLE)))
                .andExpect(jsonPath("$.author", is(TEST_AUTHOR)))
                .andExpect(jsonPath("$.publicationDate", is(TEST_DATE.toString())));
    }

    @Test
    void shouldUpdateTitle() throws Exception {
        //Given
        TitleDto titleDto = new TitleDto(TEST_ID, TEST_TITLE, TEST_AUTHOR, TEST_DATE);
        Title title = new Title(TEST_ID, TEST_TITLE, TEST_AUTHOR, TEST_DATE);

        when(titleMapper.mapToTitle(titleDto)).thenReturn(title);
        when(titleMapper.mapToTitleDto(title)).thenReturn(titleDto);
        when(titleService.updateTitle(title)).thenReturn(title);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(titleDto);

        //When & Then
        mockMvc.perform(put("/library/titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(TEST_TITLE)))
                .andExpect(jsonPath("$.author", is(TEST_AUTHOR)))
                .andExpect(jsonPath("$.publicationDate", is(TEST_DATE.toString())));
    }

    @Test
    void shouldCreateTitle() throws Exception {
        //Given
        SaveTitleDto titleDto = new SaveTitleDto(TEST_TITLE, TEST_AUTHOR, TEST_DATE);
        Title title = new Title(TEST_ID, TEST_TITLE, TEST_AUTHOR, TEST_DATE);

        when(titleMapper.mapToTitle(titleDto)).thenReturn(title);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String jsonContent = gson.toJson(titleDto);

        //When & Then
        mockMvc.perform(post("/library/titles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteTitle() throws Exception {
        //Given & When & Then
        mockMvc.perform(delete("/library/titles/1"))
                .andExpect(status().isOk());
    }
}
