package com.project.nexedu.domain.study.service;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.lecture.LectureRepository;
import com.project.nexedu.domain.study.Study;
import com.project.nexedu.domain.study.StudyRepository;
import com.project.nexedu.domain.study.dto.StudiesResponseDto;
import com.project.nexedu.domain.study.dto.StudyRequestDto;
import com.project.nexedu.domain.study.dto.StudyResponseDto;
import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public StudyResponseDto save(StudyRequestDto studyRequestDto) {
        Lecture lecture = lectureRepository.findById(studyRequestDto.getLectureId()).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));
        User user = userRepository.findById(studyRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        studyRequestDto.setLecture(lecture);
        studyRequestDto.setUser(user);

        Optional<Study> studyWrapper = studyRepository.findByLectureAndUser(lecture, user);

        return studyWrapper.map(alreadyExistingStudy ->
                        new StudyResponseDto(null, null, null))
                .orElseGet(() -> {
                    Study study = studyRequestDto.toEntity();
                    Study savedStudy = studyRepository.save(study);
                    return new StudyResponseDto(savedStudy);
                });
    }

    public StudiesResponseDto findAll() {
        List<Study> studies = studyRepository.findAllByOrderByIdDesc();

        return new StudiesResponseDto(studies);
    }

    public StudiesResponseDto findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        List<Study> studies = studyRepository.findByUserOrderByIdDesc(user);

        return new StudiesResponseDto(studies);
    }

    public StudiesResponseDto findByLectureId(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new IllegalArgumentException("강의가 없습니다."));
        List<Study> studies = studyRepository.findByLectureOrderByIdDesc(lecture);

        return new StudiesResponseDto(studies);
    }

    public StudyResponseDto findByLectureIdAndUserId(Long lectureId, Long userId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Optional<Study> studyWrapper = studyRepository.findByLectureAndUser(lecture, user);

        return studyWrapper.map(alreadyExistingStudy ->
                        new StudyResponseDto(alreadyExistingStudy))
                .orElseGet(() -> new StudyResponseDto(null, null, null));
    }

    @Transactional
    public Long update(Long id, StudyRequestDto studyRequestDto) {
        Study study = studyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 학습 내역이 없습니다."));

        study.update(studyRequestDto.getLecture(), studyRequestDto.getUser());
        return id;
    }

    @Transactional
    public Long deleteById(Long id) {
        Study study = studyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 학습 내역이 없습니다."));

        studyRepository.delete(study);
        return id;
    }

    @Transactional
    public void deleteByLectureId(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new IllegalArgumentException("강의가 없습니다."));
        List<Study> studies = studyRepository.findByLecture(lecture);

        studies.forEach(
                study -> studyRepository.deleteById(study.getId())
        );
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        List<Study> studies = studyRepository.findByUser(user);

        studies.forEach(
                study -> studyRepository.deleteById(study.getId())
        );
    }
}
