package com.project.nexedu.domain.board.dto;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private User writer;
    private String content;
    private Lecture lecture;

    @Builder
    public BoardResponseDto(Long id, String title, User writer, String content, Lecture lecture) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.lecture = lecture;
    }
}
