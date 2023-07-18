package com.project.nexedu.domain.lecture.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureUpdateRequestDto {

    private String title;
    private String description;
    private long runningTime;
    private Long instructorId;
}
