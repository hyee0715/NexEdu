package com.project.nexedu.domain.study.controller;

import com.project.nexedu.domain.study.dto.StudiesResponseDto;
import com.project.nexedu.domain.study.dto.StudyRequestDto;
import com.project.nexedu.domain.study.dto.StudyResponseDto;
import com.project.nexedu.domain.study.service.StudyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/studies")
public class StudyApiController {

    private final StudyService studyService;

    @PostMapping
    public ResponseEntity save(@RequestBody StudyRequestDto studyRequestDto) {
        StudyResponseDto studyResponseDto = studyService.save(studyRequestDto);

        if (studyResponseDto.getId() == null && studyResponseDto.getUser() == null && studyResponseDto.getLecture() == null) {
            return ResponseEntity.ok("이미 수강신청한 강의이거나 유효하지 않은 강의입니다.");
        }

        return ResponseEntity.ok(studyResponseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<StudiesResponseDto> list() {
        StudiesResponseDto studiesResponseDto = studyService.findAll();

        return ResponseEntity.ok(studiesResponseDto);
    }

    @GetMapping("/list/users/{userId}")
    public ResponseEntity<StudiesResponseDto> findByUserId(@PathVariable Long userId) {
        StudiesResponseDto studiesResponseDto = studyService.findByUserId(userId);

        return ResponseEntity.ok(studiesResponseDto);
    }

    @GetMapping("/list/lectures/{lectureId}")
    public ResponseEntity<StudiesResponseDto> findByLectureId(@PathVariable Long lectureId) {
        StudiesResponseDto studiesResponseDto = studyService.findByLectureId(lectureId);

        return ResponseEntity.ok(studiesResponseDto);
    }

    @PutMapping("/list/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody StudyRequestDto studyRequestDto) {
        Long updateId = studyService.update(id, studyRequestDto);

        return ResponseEntity.ok(updateId);
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        studyService.deleteById(id);

        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/list/lectures/{id}")
    public ResponseEntity deleteByLectureId(@PathVariable("id") Long lectureId) {
        studyService.deleteByLectureId(lectureId);

        return ResponseEntity.ok(lectureId);
    }

    @DeleteMapping("/list/users/{id}")
    public ResponseEntity deleteByUserId(@PathVariable("id") Long userId) {
        studyService.deleteByUserId(userId);

        return ResponseEntity.ok(userId);
    }
}
