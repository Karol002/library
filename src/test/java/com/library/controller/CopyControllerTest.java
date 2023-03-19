package com.library.controller;


import com.google.gson.Gson;
import com.library.controller.exception.single.CopyNotFoundException;
import com.library.controller.exception.single.TitleNotFoundException;
import com.library.domain.Copy;
import com.library.domain.Title;
import com.library.domain.dto.CopyDto;
import com.library.domain.dto.post.SaveCopyDto;
import com.library.mapper.CopyMapper;
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
@WebMvcTest(CopyController.class)
public class CopyControllerTest {
    private static final LocalDate TEST_DATE = LocalDate.of(2022, 12, 12);
    private static final Boolean TEST_STATUS = false;
    private static final String TEST_TITLE = "Test title";
    private static final String TEST_AUTHOR = "Test author";
    private static final Long TEST_TITLE_ID = 1L;
    private static final Long TEST_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CopyService copyService;

    @MockBean
    private CopyMapper copyMapper;

    @Test
    void shouldFetchCopies() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_DATE);
        List<Copy> copies = new ArrayList<>();
        Copy copy = new Copy(title);
        copies.add(copy);
        List<CopyDto> copyDtos = new ArrayList<>();
        CopyDto copyDto = new CopyDto(TEST_ID, TEST_STATUS, TEST_TITLE_ID);
        copyDtos.add(copyDto);

        when(copyService.getAllCopies()).thenReturn(copies);
        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copyDtos);

        //When & Then
        mockMvc.perform(get("/library/copies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].titleId", is(1)))
                .andExpect(jsonPath("$[0].borrowed", is(TEST_STATUS)));
    }

    @Test
    void shouldFetchCopiesForSelectTitle() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_DATE);
        List<Copy> copies = new ArrayList<>();
        Copy copy = new Copy(title);
        copies.add(copy);
        List<CopyDto> copyDtos = new ArrayList<>();
        CopyDto copyDto = new CopyDto(TEST_ID, TEST_STATUS, TEST_TITLE_ID);
        copyDtos.add(copyDto);

        when(copyService.getAllAvailable(TEST_TITLE_ID)).thenReturn(copies);
        when(copyMapper.mapToCopyDtoList(copies)).thenReturn(copyDtos);

        //When & Then
        mockMvc.perform(get("/library/copies/available/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].titleId", is(1)))
                .andExpect(jsonPath("$[0].borrowed", is(TEST_STATUS)));
    }


    @Test
    void shouldFetchCopy() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_DATE);
        Copy copy = new Copy(title);
        CopyDto copyDto = new CopyDto(TEST_ID, TEST_STATUS, TEST_TITLE_ID);

        when(copyService.getCopy(TEST_ID)).thenReturn(copy);
        when(copyMapper.mapToCopyDto(copy)).thenReturn(copyDto);

        //When & Then
        mockMvc.perform(get("/library/copies/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titleId", is(1)))
                .andExpect(jsonPath("$.borrowed", is(TEST_STATUS)));
    }

    @Test
    void shouldCreateCopy() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_DATE);
        Copy copy = new Copy(title);
        CopyDto copyDto = new CopyDto(TEST_ID, TEST_STATUS, TEST_TITLE_ID);

        when(copyMapper.mapToCopy(copyDto)).thenReturn(copy);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(copyDto);

        //When & Then
        mockMvc.perform(post("/library/copies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowCopyNotFoundException() throws Exception {
        //Given
        Title title = new Title(TEST_TITLE_ID, TEST_TITLE, TEST_AUTHOR, TEST_DATE);
        Copy copy = new Copy(title);
        CopyDto copyDto = new CopyDto(TEST_ID, TEST_STATUS, TEST_TITLE_ID);

        when(copyMapper.mapToCopy(copyDto)).thenReturn(copy);
        when(copyService.getCopy(TEST_ID)).thenThrow(CopyNotFoundException.class);
        doThrow(CopyNotFoundException.class).when(copyService).deleteCopy(TEST_ID);

        //When & Then
        mockMvc.perform(get("/library/copies/1"))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/library/copies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldThrowTitleNotFoundException() throws Exception {
        //Given
        SaveCopyDto copyDto = new SaveCopyDto(TEST_TITLE_ID);

        when(copyMapper.mapToCopy(copyDto)).thenThrow(TitleNotFoundException.class);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(copyDto);

        //When & Then
        mockMvc.perform(post("/library/copies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isNotFound());
    }
}
