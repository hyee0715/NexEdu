package com.project.nexedu.domain.lecture.dto;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureSaveRequestDto {

    private String title;
    private User instructor;
    private String description;
    private long runningTime;
    private Long instructorId;

    @Builder
    public LectureSaveRequestDto(String title, User instructor, String description, long runningTime, Long instructorId) {
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.runningTime = runningTime;
        this.instructorId = instructorId;
    }

    public Lecture toEntity() {
        return Lecture.builder()
                .title(title)
                .instructor(instructor)
                .description(description)
                .runningTime(runningTime)
                .build();
    }
}
