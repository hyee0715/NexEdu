package com.project.nexedu.domain.study.dto;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.study.Study;
import com.project.nexedu.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudyRequestDto {

    private Lecture lecture;
    private User user;
    private Long lectureId;
    private Long userId;

    @Builder
    public StudyRequestDto(Lecture lecture, User user, Long lectureId, Long userId) {
        this.lecture = lecture;
        this.user = user;
        this.lectureId = lectureId;
        this.userId = userId;
    }

    public Study toEntity() {
        return Study.builder()
                .lecture(lecture)
                .user(user)
                .build();
    }
}
