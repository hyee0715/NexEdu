package com.project.nexedu.domain.lecture.service;

import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.lecture.LectureRepository;
import com.project.nexedu.domain.lecture.dto.LectureResponseDto;
import com.project.nexedu.domain.lecture.dto.LectureSaveRequestDto;
import com.project.nexedu.domain.lecture.dto.LectureUpdateRequestDto;
import com.project.nexedu.domain.lecture.dto.LecturesResponseDto;
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

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@TestPropertySource(locations = "classpath:/application-test.properties")
@SpringBootTest
class LectureServiceTest {

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LectureService lectureService;

    private LectureSaveRequestDto lectureSaveRequestDto;

    @BeforeEach
    public void setUp() {
        UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto("testRealName", "testUsername", "testNickname", encodePassword("testPassword"), "testEmail", Role.USER);
        User savedUser = userRepository.save(userSignUpRequestDto.toEntity());

        lectureSaveRequestDto = new LectureSaveRequestDto("testTitle", savedUser, "testDescription", 10L, savedUser.getId());
    }

    @DisplayName("강의를 저장한다")
    @Test
    void save() {
        //given
        //when
        LectureResponseDto lectureResponseDto = lectureService.save(lectureSaveRequestDto);

        //then
        assertThat(lectureResponseDto.getTitle()).isEqualTo("testTitle");
    }

    @DisplayName("저장된 강의를 모두 조회한다")
    @Test
    void findAll() {
        //given
        lectureService.save(lectureSaveRequestDto);

        //when
        LecturesResponseDto lecturesResponseDto = lectureService.findAll();

        //then
        assertThat(lecturesResponseDto.getLectures().size()).isEqualTo(1);
    }

    @DisplayName("강의 id로 강의를 찾는다")
    @Test
    void findById() {
        //given
        LectureResponseDto lectureResponseDto = lectureService.save(lectureSaveRequestDto);

        //when
        LectureResponseDto result = lectureService.findById(lectureResponseDto.getId());

        //then
        assertThat(result.getId()).isEqualTo(lectureResponseDto.getId());
    }

    @DisplayName("강사 id로 강의를 찾는다")
    @Test
    void findByInstructorId() {
        //given
        LectureResponseDto lectureResponseDto = lectureService.save(lectureSaveRequestDto);

        //when
        LecturesResponseDto lecturesResponseDto = lectureService.findByInstructorId(lectureResponseDto.getInstructor().getId());

        List<String> lectureTitles = lecturesResponseDto.getLectures().stream()
                .map(Lecture::getTitle)
                .collect(Collectors.toList());

        //then
        assertThat(lectureTitles).contains(lectureResponseDto.getTitle());
    }

    @DisplayName("강의를 수정한다")
    @Test
    void update() {
        //given
        LectureResponseDto lectureResponseDto = lectureService.save(lectureSaveRequestDto);

        LectureUpdateRequestDto lectureUpdateRequestDto = new LectureUpdateRequestDto();
        lectureUpdateRequestDto.setTitle("updatedTitle");
        lectureUpdateRequestDto.setDescription("updatedDescription");
        lectureUpdateRequestDto.setInstructorId(lectureResponseDto.getInstructor().getId());
        lectureUpdateRequestDto.setRunningTime(1L);

        //when
        Long updatedLectureId = lectureService.update(lectureResponseDto.getId(), lectureUpdateRequestDto);

        LectureResponseDto result = lectureService.findById(updatedLectureId);

        //then
        assertThat(result.getTitle()).isEqualTo("updatedTitle");
    }

    @DisplayName("강의를 삭제한다")
    @Test
    void delete() {
        //given
        LectureResponseDto lectureResponseDto = lectureService.save(lectureSaveRequestDto);

        //when
        lectureService.delete(lectureResponseDto.getId());

        LecturesResponseDto lecturesResponseDto = lectureService.findAll();

        //then
        assertThat(lecturesResponseDto.getLectures()).isEmpty();
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}