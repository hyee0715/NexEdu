package com.project.nexedu.domain.comment.dto;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private Board board;
    private User writer;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public CommentResponseDto(Long id, String content, Board board, User writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.content = content;
        this.board = board;
        this.writer = writer;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
