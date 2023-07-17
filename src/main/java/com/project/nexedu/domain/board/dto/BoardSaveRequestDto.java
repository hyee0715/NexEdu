package com.project.nexedu.domain.board.dto;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardSaveRequestDto {

    private String title;
    private User writer;
    private String content;
    private Lecture lecture;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .lecture(lecture)
                .build();
    }
}
