package com.project.nexedu.domain.lecture.dto;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class LectureResponseDto {

    private Long id;
    private String title;
    private User instructor;
    private String description;
    private long runningTime;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public LectureResponseDto(Lecture lecture) {
        this.id = lecture.getId();
        this.title = lecture.getTitle();
        this.instructor = lecture.getInstructor();
        this.description = lecture.getDescription();
        this.runningTime = lecture.getRunningTime();
        this.createdDate = lecture.getCreatedDate();
        this.modifiedDate = lecture.getModifiedDate();
    }
}
