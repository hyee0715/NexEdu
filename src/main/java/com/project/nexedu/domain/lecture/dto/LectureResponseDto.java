package com.project.nexedu.domain.lecture.dto;

import com.project.nexedu.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureResponseDto {

    private Long id;
    private String title;
    private User instructor;
    private String description;
    private long runningTime;

    @Builder
    public LectureResponseDto(Long id, String title, User instructor, String description, long runningTime) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.runningTime = runningTime;
    }
}
