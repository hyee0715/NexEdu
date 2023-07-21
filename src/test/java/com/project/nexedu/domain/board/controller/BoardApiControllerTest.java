package com.project.nexedu.domain.board.controller;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.board.dto.*;
import com.project.nexedu.domain.board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.nexedu.domain.board.dto.BoardSaveRequestDto;
import com.project.nexedu.domain.board.dto.BoardResponseDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

class BoardApiControllerTest {

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardApiController boardApiController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(boardApiController).build();
    }

    @DisplayName("게시글을 저장한다")
    @Test
    public void save() throws Exception {
        //given
        BoardSaveRequestDto requestDto = new BoardSaveRequestDto();
        requestDto.setTitle("testTitle");
        requestDto.setContent("testContent");

        BoardResponseDto responseDto = new BoardResponseDto(111L, "testTitle", null, "testContent", null, null, null, null);

        when(boardService.save(any(Long.class), any(BoardSaveRequestDto.class)))
                .thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(post("/api/boards/{lectureId}", 123L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 목록을 조회한다")
    @Test
    void list() throws Exception {
        //given
        BoardsResponseDto boardsResponseDto = new BoardsResponseDto(Collections.emptyList());
        when(boardService.findAll()).thenReturn(boardsResponseDto);

        //when
        //then
        mockMvc.perform(get("/api/boards/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.boards").isArray());
    }

    @DisplayName("강의 id로 게시글들을 조회한다")
    @Test
    public void findByLectureId() throws Exception {
        List<Board> Boards = new ArrayList<>();
        Board board1 = new Board(1L, "Board1", null, "Content1", null);
        Boards.add(board1);

        Board board2 = new Board(2L, "Board2", null, "Content2", null);
        Boards.add(board2);

        when(boardService.findByLectureId(anyLong()))
                .thenReturn(new BoardsResponseDto(Boards));

        //when
        //then
        mockMvc.perform(get("/api/boards/list/{lectureId}", 123L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boards[0].id").value(1))
                .andExpect(jsonPath("$.boards[0].title").value("Board1"))
                .andExpect(jsonPath("$.boards[0].content").value("Content1"))
                .andExpect(jsonPath("$.boards[1].id").value(2))
                .andExpect(jsonPath("$.boards[1].title").value("Board2"))
                .andExpect(jsonPath("$.boards[1].content").value("Content2"));
    }

    @DisplayName("게시글 상세 정보를 조회한다")
    @Test
    public void detail() throws Exception {
        //given
        BoardResponseDto boardResponseDto = new BoardResponseDto(1L, "Board1", null, "Content1", null, null, null, null);

        when(boardService.findById(anyLong()))
                .thenReturn(boardResponseDto);

        //when
        //then
        mockMvc.perform(get("/api/boards/detail/{boardId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Board1"))
                .andExpect(jsonPath("$.content").value("Content1"));
    }

    @DisplayName("게시글을 수정한다")
    @Test
    public void update() throws Exception {
        //given
        BoardUpdateRequestDto requestDto = new BoardUpdateRequestDto();
        requestDto.setTitle("updatedTitle");
        requestDto.setContent("updatedContent");

        Long updatedId = 1L;
        when(boardService.update(anyLong(), any(BoardUpdateRequestDto.class)))
                .thenReturn(updatedId);

        //when
        //then
        mockMvc.perform(put("/api/boards/detail/{boardId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글을 삭제한다")
    @Test
    public void deleteBoard() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete("/api/boards/detail/{boardId}", 111L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}