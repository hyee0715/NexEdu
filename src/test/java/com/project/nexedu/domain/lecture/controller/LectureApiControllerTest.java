package com.project.nexedu.domain.lecture.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.lecture.dto.LectureResponseDto;
import com.project.nexedu.domain.lecture.dto.LectureSaveRequestDto;
import com.project.nexedu.domain.lecture.dto.LectureUpdateRequestDto;
import com.project.nexedu.domain.lecture.dto.LecturesResponseDto;
import com.project.nexedu.domain.lecture.service.LectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LectureApiControllerTest {

    @Mock
    private LectureService lectureService;

    @InjectMocks
    private LectureApiController lectureApiController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(lectureApiController).build();
    }

    @DisplayName("강의를 저장한다")
    @Test
    void save() throws Exception {
        //given
        LectureSaveRequestDto requestDto = LectureSaveRequestDto.builder()
                .title("testLecture")
                .instructorId(1L)
                .description("testLectureDescription")
                .runningTime(60L)
                .build();

        LectureResponseDto responseDto = new LectureResponseDto(1L, "testLecture", null, "testLectureDescription", 60L, null, null);

        when(lectureService.save(any(LectureSaveRequestDto.class))).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(post("/api/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @DisplayName("강의 목록을 조회한다")
    @Test
    void list() throws Exception {
        //given
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(new Lecture(1L, "Lecture1", null, "Description1", 60L));
        lectures.add(new Lecture(2L, "Lecture2", null, "Description2", 45L));

        LecturesResponseDto responseDto = new LecturesResponseDto(lectures);

        when(lectureService.findAll()).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(get("/api/lectures/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lectures.length()").value(2))
                .andReturn();
    }

    @DisplayName("강의 상세 보기를 진행한다")
    @Test
    void detail() throws Exception {
        //given
        Lecture lecture = new Lecture(1L, "TestLecture", null, "testLectureDescription", 60L);

        LectureResponseDto responseDto = new LectureResponseDto(lecture.getId(), lecture.getTitle(), lecture.getInstructor(), lecture.getDescription(), lecture.getRunningTime(), lecture.getCreatedDate(), lecture.getModifiedDate());

        when(lectureService.findById(1L)).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(get("/api/lectures/detail/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("TestLecture"))
                .andReturn();
    }

    @DisplayName("강의를 수정한다")
    @Test
    void update() throws Exception {
        //given
        LectureUpdateRequestDto requestDto = new LectureUpdateRequestDto();
        requestDto.setTitle("UpdatedLecture");
        requestDto.setDescription("updatedLectureDescription");
        requestDto.setRunningTime(90L);

        when(lectureService.update(1L, requestDto)).thenReturn(1L);

        //when
        //then
        mockMvc.perform(put("/api/lectures/detail/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"UpdatedLecture\",\"description\":\"updatedLectureDescription\",\"runningTime\":90}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @DisplayName("강의를 삭제한다")
    @Test
    void deleteTest() throws Exception {
        //given
        when(lectureService.delete(1L)).thenReturn(1L);

        //when
        //then
        mockMvc.perform(delete("/api/lectures/detail/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}