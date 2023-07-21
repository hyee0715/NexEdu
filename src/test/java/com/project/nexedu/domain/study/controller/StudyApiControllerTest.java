package com.project.nexedu.domain.study.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.nexedu.domain.study.dto.StudiesResponseDto;
import com.project.nexedu.domain.study.dto.StudyRequestDto;
import com.project.nexedu.domain.study.dto.StudyResponseDto;
import com.project.nexedu.domain.study.service.StudyService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudyApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudyService studyService;

    @InjectMocks
    private StudyApiController studyApiController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studyApiController).build();
    }

    @DisplayName("수강 신청을 처리한다")
    @Test
    public void save() throws Exception {
        //given
        StudyRequestDto requestDto = StudyRequestDto.builder()
                .lectureId(1L)
                .userId(1L)
                .build();

        StudyResponseDto responseDto = StudyResponseDto.builder()
                .id(1L)
                .lecture(requestDto.getLecture())
                .user(requestDto.getUser())
                .build();

        when(studyService.save(any(StudyRequestDto.class))).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(post("/api/studies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @DisplayName("수강 신청 목록을 조회한다")
    @Test
    public void list() throws Exception {
        //given
        StudiesResponseDto responseDto = new StudiesResponseDto(Collections.emptyList());

        when(studyService.findAll()).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(get("/api/studies/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studies").isArray());
    }

    @DisplayName("회원 id로 수강 신청 목록을 조회한다")
    @Test
    public void findByUserId() throws Exception {
        //given
        StudiesResponseDto responseDto = new StudiesResponseDto(Collections.emptyList());

        when(studyService.findByUserId(anyLong())).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(get("/api/studies/list/users/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studies").isArray());
    }

    @DisplayName("강의 id로 수강 신청 목록을 조회한다")
    @Test
    public void findByLectureId() throws Exception {
        //given
        StudiesResponseDto responseDto = new StudiesResponseDto(Collections.emptyList());

        when(studyService.findByLectureId(anyLong())).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(get("/api/studies/list/lectures/{lectureId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studies").isArray());
    }

    @DisplayName("수강 신청 목록을 수정한다")
    @Test
    public void update() throws Exception {
        //given
        StudyRequestDto requestDto = StudyRequestDto.builder()
                .lectureId(1L)
                .userId(1L)
                .build();

        //when
        //then
        mockMvc.perform(put("/api/studies/list/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @DisplayName("id로 수강 신청 목록을 삭제한다")
    @Test
    public void deleteById() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/api/studies/list/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("강의 id로 수강 신청 목록을 삭제한다")
    @Test
    public void deleteByLectureId() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/api/studies/list/lectures/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @DisplayName("회원 id로 수강 신청 목록을 삭제한다")
    @Test
    public void deleteByUserId() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/api/studies/list/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}