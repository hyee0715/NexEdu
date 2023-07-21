package com.project.nexedu.domain.study.service;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.lecture.LectureRepository;
import com.project.nexedu.domain.lecture.dto.LectureSaveRequestDto;
import com.project.nexedu.domain.study.Study;
import com.project.nexedu.domain.study.StudyRepository;
import com.project.nexedu.domain.study.dto.StudiesResponseDto;
import com.project.nexedu.domain.study.dto.StudyRequestDto;
import com.project.nexedu.domain.study.dto.StudyResponseDto;
import com.project.nexedu.domain.user.Role;
import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.UserRepository;
import com.project.nexedu.domain.user.dto.UserSignUpRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@Transactional
@TestPropertySource(locations = "classpath:/application-test.properties")
@SpringBootTest
class StudyServiceTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private StudyService studyService;

    private User savedUser;
    private Lecture savedLecture;
    private StudyRequestDto studyRequestDto;

    @BeforeEach
    public void setUp() {
        UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto("testRealName", "testUsername", "testNickname", encodePassword("testPassword"), "testEmail", Role.USER);
        savedUser = userRepository.save(userSignUpRequestDto.toEntity());

        LectureSaveRequestDto lectureSaveRequestDto = new LectureSaveRequestDto("testTitle", savedUser, "testDescription", 10L, savedUser.getId());
        savedLecture = lectureRepository.save(lectureSaveRequestDto.toEntity());

        studyRequestDto = new StudyRequestDto(savedLecture, savedUser, savedLecture.getId(), savedUser.getId());
    }

    @DisplayName("수강 신청을 처리한다")
    @Test
    void save() {
        //given
        //when
        StudyResponseDto studyResponseDto = studyService.save(studyRequestDto);

        //then
        assertThat(studyResponseDto.getLecture().getTitle()).isEqualTo("testTitle");
    }

    @DisplayName("수강 신청 목록을 조회한다")
    @Test
    void findAll() {
        //given
        studyService.save(studyRequestDto);

        //when
        StudiesResponseDto studiesResponseDto = studyService.findAll();

        //then
        assertThat(studiesResponseDto.getStudies().size()).isEqualTo(1);
    }

    @DisplayName("회원 id로 수강 신청 목록을 조회한다")
    @Test
    void findByUserId() {
        //given
        StudyResponseDto studyResponseDto = studyService.save(studyRequestDto);

        //when
        StudiesResponseDto studiesResponseDto = studyService.findByUserId(studyResponseDto.getUser().getId());

        List<String> studyLectureTitles = studiesResponseDto.getStudies().stream()
                .map(s -> s.getLecture().getTitle())
                .collect(Collectors.toList());
        //then
        assertThat(studyLectureTitles).contains("testTitle");
    }

    @DisplayName("강의 id로 수강 신청 목록을 조회한다")
    @Test
    void findByLectureId() {
        //given
        StudyResponseDto studyResponseDto = studyService.save(studyRequestDto);

        //when
        StudiesResponseDto studiesResponseDto = studyService.findByLectureId(studyResponseDto.getLecture().getId());
        List<String> studyUserReaNames = studiesResponseDto.getStudies().stream()
                .map(s -> s.getUser().getRealName())
                .toList();

        //then
        assertThat(studyUserReaNames).contains("testRealName");
    }

    @DisplayName("회원 id와 강의 id로 수강 신청 목록을 조회한다")
    @Test
    void findByLectureIdAndUserId() {
        //given
        StudyResponseDto studyResponseDto = studyService.save(studyRequestDto);

        //when
        StudyResponseDto result = studyService.findByLectureIdAndUserId(studyResponseDto.getLecture().getId(), studyResponseDto.getUser().getId());

        //then
        assertThat(result.getLecture().getTitle()).isEqualTo(studyResponseDto.getLecture().getTitle());
        assertThat(result.getUser().getNickname()).isEqualTo(studyResponseDto.getUser().getNickname());
    }

    @DisplayName("수강 신청 목록을 수정한다")
    @Test
    void update() {
        //given
        StudyResponseDto studyResponseDto = studyService.save(studyRequestDto);

        UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto("updateRealName", "testUsername", "testNickname", encodePassword("testPassword"), "testEmail", Role.USER);
        savedUser = userRepository.save(userSignUpRequestDto.toEntity());

        LectureSaveRequestDto lectureSaveRequestDto = new LectureSaveRequestDto("updateTitle", savedUser, "testDescription", 10L, savedUser.getId());
        savedLecture = lectureRepository.save(lectureSaveRequestDto.toEntity());

        studyRequestDto = new StudyRequestDto(savedLecture, savedUser, savedLecture.getId(), savedUser.getId());

        //when
        Long updatedStudyId = studyService.update(studyResponseDto.getId(), studyRequestDto);

        Study study = studyRepository.findById(updatedStudyId).get();

        //then
        assertThat(study.getLecture().getTitle()).isEqualTo("updateTitle");
        assertThat(study.getUser().getRealName()).isEqualTo("updateRealName");
    }

    @DisplayName("id로 수강 신청 목록을 삭제한다")
    @Test
    void deleteById() {
        //given
        StudyResponseDto studyResponseDto = studyService.save(studyRequestDto);

        //when
        studyService.deleteById(studyResponseDto.getId());

        StudiesResponseDto studiesResponseDto = studyService.findAll();

        //then
        assertThat(studiesResponseDto.getStudies()).isEmpty();
    }

    @DisplayName("강의 id로 수강 신청 목록을 삭제한다")
    @Test
    void deleteByLectureId() {
        //given
        StudyResponseDto studyResponseDto = studyService.save(studyRequestDto);

        //when
        studyService.deleteByLectureId(studyResponseDto.getLecture().getId());

        StudiesResponseDto studiesResponseDto = studyService.findAll();

        //then
        assertThat(studiesResponseDto.getStudies()).isEmpty();
    }

    @DisplayName("회원 id로 수강 신청 목록을 삭제한다")
    @Test
    void deleteByUserId() {
        //given
        StudyResponseDto studyResponseDto = studyService.save(studyRequestDto);

        //when
        studyService.deleteByUserId(studyResponseDto.getUser().getId());

        StudiesResponseDto studiesResponseDto = studyService.findAll();

        //then
        assertThat(studiesResponseDto.getStudies()).isEmpty();
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}