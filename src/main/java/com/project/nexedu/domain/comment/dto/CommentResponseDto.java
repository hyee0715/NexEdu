package com.project.nexedu.domain.comment.dto;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.comment.Comment;
import com.project.nexedu.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private Board board;
    private User writer;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.board = comment.getBoard();
        this.writer = comment.getWriter();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
