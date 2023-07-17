package com.project.nexedu.domain.lecture.dto;

import com.project.nexedu.domain.lecture.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LecturesResponseDto {

    private List<Lecture> lectures;
}
