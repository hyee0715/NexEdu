package com.project.nexedu.domain.lecture.service;

import com.project.nexedu.domain.board.BoardRepository;
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
    private final BoardRepository boardRepository;

    @Transactional
    public LectureResponseDto save(LectureSaveRequestDto lectureSaveRequestDto) {
        User user = userRepository.findById(lectureSaveRequestDto.getInstructorId()).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        lectureSaveRequestDto.setInstructor(user);

        Lecture lecture = lectureSaveRequestDto.toEntity();
        Lecture savedLecture = lectureRepository.save(lecture);

        return new LectureResponseDto(savedLecture);
    }

    public LecturesResponseDto findAll() {
        List<Lecture> lectures = lectureRepository.findAllByOrderByIdDesc();

        return new LecturesResponseDto(lectures);
    }

    public LectureResponseDto findById(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));

        return new LectureResponseDto(lecture);
    }

    public LecturesResponseDto findByInstructorId(Long instructorId) {
        User instructor = userRepository.findById(instructorId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        List<Lecture> lectures = lectureRepository.findByInstructorOrderByIdDesc(instructor);

        return new LecturesResponseDto(lectures);
    }

    @Transactional
    public Long update(Long id, LectureUpdateRequestDto lectureUpdateRequestDto) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));

        lecture.update(lectureUpdateRequestDto.getTitle(), lectureUpdateRequestDto.getDescription(), lectureUpdateRequestDto.getRunningTime());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));

        studyRepository.deleteByLecture(lecture);
        boardRepository.deleteByLecture(lecture);
        lectureRepository.delete(lecture);

        return id;
    }
}
