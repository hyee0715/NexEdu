package com.project.nexedu.domain.lecture.controller;

import com.project.nexedu.domain.lecture.dto.LectureResponseDto;
import com.project.nexedu.domain.lecture.dto.LectureSaveRequestDto;
import com.project.nexedu.domain.lecture.dto.LectureUpdateRequestDto;
import com.project.nexedu.domain.lecture.dto.LecturesResponseDto;
import com.project.nexedu.domain.lecture.service.LectureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/lecture")
public class LectureApiController {

    private final LectureService lectureService;

    @PostMapping("/save")
    public ResponseEntity<LectureResponseDto> save(@RequestBody LectureSaveRequestDto lectureSaveRequestDto) {
        LectureResponseDto lectureResponseDto = lectureService.save(lectureSaveRequestDto);

        return ResponseEntity.ok(lectureResponseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<LecturesResponseDto> list() {
        LecturesResponseDto lecturesResponseDto = lectureService.findAll();

        return ResponseEntity.ok(lecturesResponseDto);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<LectureResponseDto> detail(@PathVariable("id") Long id) {
        LectureResponseDto lectureResponseDto = lectureService.findById(id);

        return ResponseEntity.ok(lectureResponseDto);
    }

    @PutMapping("/detail/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody LectureUpdateRequestDto lectureUpdateRequestDto) {
        Long updateId = lectureService.update(id, lectureUpdateRequestDto);

        return ResponseEntity.ok(updateId);
    }

    @DeleteMapping("/detail/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        lectureService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
