package com.project.nexedu.domain.study.dto;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudyResponseDto {

    private Long id;
    private Lecture lecture;
    private User user;

    @Builder
    public StudyResponseDto(Long id, Lecture lecture, User user) {
        this.id = id;
        this.lecture = lecture;
        this.user = user;
    }
}
