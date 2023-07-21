package com.project.nexedu.domain.study.dto;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.study.Study;
import com.project.nexedu.domain.user.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class StudyResponseDto {

    private Long id;
    private Lecture lecture;
    private User user;

    public StudyResponseDto(Study study) {
        this.id = study.getId();
        this.lecture = study.getLecture();
        this.user = study.getUser();
    }
}
