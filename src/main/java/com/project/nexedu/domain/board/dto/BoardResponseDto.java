package com.project.nexedu.domain.board.dto;

import com.project.nexedu.domain.comment.Comment;
import com.project.nexedu.domain.comment.dto.CommentResponseDto;
import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private User writer;
    private String content;
    private Lecture lecture;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<Comment> comments;

    @Builder
    public BoardResponseDto(Long id, String title, User writer, String content, Lecture lecture, LocalDateTime createdDate, LocalDateTime modifiedDate, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.lecture = lecture;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.comments = comments;
    }
}
