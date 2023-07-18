package com.project.nexedu.domain.lecture.dto;

import com.project.nexedu.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class LectureResponseDto {

    private Long id;
    private String title;
    private User instructor;
    private String description;
    private long runningTime;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder
    public LectureResponseDto(Long id, String title, User instructor, String description, long runningTime, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.runningTime = runningTime;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
