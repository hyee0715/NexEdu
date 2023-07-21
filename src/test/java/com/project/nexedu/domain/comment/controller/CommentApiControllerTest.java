package com.project.nexedu.domain.comment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.nexedu.domain.comment.dto.CommentResponseDto;
import com.project.nexedu.domain.comment.dto.CommentSaveRequestDto;
import com.project.nexedu.domain.comment.dto.CommentUpdateRequestDto;
import com.project.nexedu.domain.comment.service.CommentService;
import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentService commentService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CommentApiController commentApiController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentApiController).build();
    }

    @DisplayName("댓글을 저장한다")
    @Test
    public void saveComment() throws Exception {
        //given
        Long boardId = 1L;
        String content = "TestCommentContent";
        CommentSaveRequestDto requestDto = new CommentSaveRequestDto();
        requestDto.setContent(content);

        User user = new User();

        CommentResponseDto responseDto = new CommentResponseDto(1L, null, null, null, null, null);

        when(userService.getCurrentUser()).thenReturn(user);
        when(commentService.save(any(Long.class), any(CommentSaveRequestDto.class), any(User.class))).thenReturn(responseDto);

        //when
        //then
        mockMvc.perform(post("/api/boards/detail/{boardId}", boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isOk());
    }

    @DisplayName("댓글을 수정한다")
    @Test
    public void updateComment() throws Exception {
        //given
        Long boardId = 1L;
        Long commentId = 1L;
        String updatedContent = "UpdatedCommentContent";
        CommentUpdateRequestDto requestDto = new CommentUpdateRequestDto();
        requestDto.setContent(updatedContent);

        when(commentService.update(any(Long.class), any(CommentUpdateRequestDto.class))).thenReturn(commentId);

        //when
        //then
        mockMvc.perform(put("/api/boards/detail/{boardId}/comments/{commentId}", boardId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDto)))
                .andExpect(status().isOk());
    }

    @DisplayName("댓글을 삭제한다")
    @Test
    public void deleteComment() throws Exception {
        //given
        Long boardId = 1L;
        Long commentId = 1L;

        //when
        //then
        mockMvc.perform(delete("/api/boards/detail/{boardId}/comments/{commentId}", boardId, commentId))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}