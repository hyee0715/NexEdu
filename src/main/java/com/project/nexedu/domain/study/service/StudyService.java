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
        Optional<Study> studyWrapper = studyRepository.findByLectureAndUser(studyRequestDto.getLecture(), studyRequestDto.getUser());

        return studyWrapper.map(existingStudy ->
                        new StudyResponseDto(null, null, null))
                .orElseGet(() -> {
                    Study study = studyRequestDto.toEntity();
                    Study savedStudy = studyRepository.save(study);
                    return new StudyResponseDto(savedStudy.getId(), savedStudy.getLecture(), savedStudy.getUser());
                });
    }

    public StudiesResponseDto findAll() {
        List<Study> studies = studyRepository.findAll();

        return new StudiesResponseDto(studies);
    }

    public StudiesResponseDto findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 없습니다."));
        List<Study> studies = studyRepository.findByUser(user);

        return new StudiesResponseDto(studies);
    }

    public StudiesResponseDto findByLectureId(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new IllegalArgumentException("강의가 없습니다."));
        List<Study> studies = studyRepository.findByLecture(lecture);

        return new StudiesResponseDto(studies);
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
