package com.project.nexedu.domain.lecture.service;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.lecture.LectureRepository;
import com.project.nexedu.domain.lecture.dto.LectureResponseDto;
import com.project.nexedu.domain.lecture.dto.LectureSaveRequestDto;
import com.project.nexedu.domain.lecture.dto.LectureUpdateRequestDto;
import com.project.nexedu.domain.lecture.dto.LecturesResponseDto;
import com.project.nexedu.domain.study.StudyRepository;
import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;

    @Transactional
    public LectureResponseDto save(LectureSaveRequestDto lectureSaveRequestDto) {
        User user = userRepository.findById(lectureSaveRequestDto.getInstructorId()).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        lectureSaveRequestDto.setInstructor(user);

        Lecture lecture = lectureSaveRequestDto.toEntity();
        Lecture savedLecture = lectureRepository.save(lecture);

        return new LectureResponseDto(savedLecture.getId(), savedLecture.getTitle(), savedLecture.getInstructor(), savedLecture.getDescription(), savedLecture.getRunningTime(), savedLecture.getCreatedDate(), savedLecture.getModifiedDate());
    }

    public LecturesResponseDto findAll() {
        List<Lecture> lectures = lectureRepository.findAll();

        return new LecturesResponseDto(lectures);
    }

    public LectureResponseDto findById(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(RuntimeException::new);

        return new LectureResponseDto(lecture.getId(), lecture.getTitle(), lecture.getInstructor(), lecture.getDescription(), lecture.getRunningTime(), lecture.getCreatedDate(), lecture.getModifiedDate());
    }

    @Transactional
    public Long update(Long id, LectureUpdateRequestDto lectureUpdateRequestDto) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(RuntimeException::new);

        lecture.update(lectureUpdateRequestDto.getTitle(), lectureUpdateRequestDto.getDescription(), lectureUpdateRequestDto.getRunningTime());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(RuntimeException::new);

        studyRepository.deleteByLecture(lecture);
        lectureRepository.delete(lecture);
        return id;
    }
}
