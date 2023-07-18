package com.project.nexedu.domain.comment.dto;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.comment.Comment;
import com.project.nexedu.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentSaveRequestDto {

    private String content;
    private Board board;
    private User writer;

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .board(board)
                .writer(writer)
                .build();
    }
}
