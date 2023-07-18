package com.project.nexedu.domain.study.dto;

import com.project.nexedu.domain.study.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StudiesResponseDto {

    private List<Study> studies;
}
