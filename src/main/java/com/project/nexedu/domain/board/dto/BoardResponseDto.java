package com.project.nexedu.domain.board.dto;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.comment.Comment;
import com.project.nexedu.domain.comment.dto.CommentResponseDto;
import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private User writer;
    private String content;
    private Lecture lecture;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<Comment> comments;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.writer = board.getWriter();
        this.content = board.getContent();
        this.lecture = board.getLecture();
        this.createdDate = board.getCreatedDate();
        this.modifiedDate = board.getModifiedDate();
        this.comments = board.getComments();
    }
}
