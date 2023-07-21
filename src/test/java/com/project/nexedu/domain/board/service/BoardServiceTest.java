package com.project.nexedu.domain.board.service;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.board.BoardRepository;
import com.project.nexedu.domain.board.dto.BoardResponseDto;
import com.project.nexedu.domain.board.dto.BoardSaveRequestDto;
import com.project.nexedu.domain.board.dto.BoardUpdateRequestDto;
import com.project.nexedu.domain.board.dto.BoardsResponseDto;
import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.lecture.LectureRepository;
import com.project.nexedu.domain.lecture.dto.LectureSaveRequestDto;
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
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardService boardService;

    private User savedUser;
    private Lecture savedLecture;
    private BoardSaveRequestDto boardSaveRequestDto;

    @BeforeEach
    public void setUp() {
        UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto("testRealName", "testUsername", "testNickname", encodePassword("testPassword"), "testEmail", Role.USER);
        savedUser = userRepository.save(userSignUpRequestDto.toEntity());

        LectureSaveRequestDto lectureSaveRequestDto = new LectureSaveRequestDto("testTitle", savedUser, "testDescription", 10L, savedUser.getId());
        Lecture lecture = lectureSaveRequestDto.toEntity();
        savedLecture = lectureRepository.save(lecture);

        boardSaveRequestDto = new BoardSaveRequestDto();
        boardSaveRequestDto.setTitle("testBoardTitle");
        boardSaveRequestDto.setWriter(savedUser);
        boardSaveRequestDto.setContent("testBoardContent");
        boardSaveRequestDto.setLecture(savedLecture);
        boardSaveRequestDto.setWriterId(savedUser.getId());
        boardSaveRequestDto.setLectureId(savedLecture.getId());
    }

    @DisplayName("게시글을 저장한다")
    @Test
    public void save() {
        // given
        BoardResponseDto responseDto = boardService.save(savedLecture.getId(), boardSaveRequestDto);

        // when
        Board savedBoard = boardRepository.findById(responseDto.getId()).orElseThrow(() -> new RuntimeException("Saved board not found."));

        // then
        assertThat("testBoardTitle").isEqualTo(savedBoard.getTitle());
        assertThat(savedUser).isEqualTo(savedBoard.getWriter());
        assertThat("testBoardContent").isEqualTo(savedBoard.getContent());
        assertThat(savedLecture).isEqualTo(savedBoard.getLecture());
    }

    @DisplayName("저장된 게시글을 모두 조회한다")
    @Test
    void findAll() {
        // given
       boardService.save(savedLecture.getId(), boardSaveRequestDto);

        // when
        BoardsResponseDto boardsResponseDto = boardService.findAll();

        // then
        assertThat(boardsResponseDto.getBoards().size()).isEqualTo(1);
    }

    @DisplayName("게시글 id로 게시글을 찾는다")
    @Test
    void findById() {
        // given
        BoardResponseDto responseDto = boardService.save(savedLecture.getId(), boardSaveRequestDto);

        // when
        BoardResponseDto boardResponseDto = boardService.findById(responseDto.getId());

        // then
        assertThat(boardResponseDto.getId()).isEqualTo(responseDto.getId());
    }

    @DisplayName("회원 id로 게시글을 찾는다")
    @Test
    void findByUserId() {
        //given
        BoardResponseDto responseDto = boardService.save(savedLecture.getId(), boardSaveRequestDto);

        //when
        BoardsResponseDto boardsResponseDto = boardService.findByLectureId(savedLecture.getId());
        List<Long> boardIds = boardsResponseDto.getBoards().stream()
                .map(Board::getId)
                .collect(Collectors.toList());

        //then
        assertThat(boardIds).contains(responseDto.getId());
    }

    @DisplayName("게시글을 수정한다")
    @Test
    void update() {
        //given
        BoardResponseDto responseDto = boardService.save(savedLecture.getId(), boardSaveRequestDto);

        BoardUpdateRequestDto boardUpdateRequestDto = new BoardUpdateRequestDto();
        boardUpdateRequestDto.setTitle("updatedTitle");
        boardUpdateRequestDto.setWriterId(4L);
        boardUpdateRequestDto.setContent("updatedContent");

        //when
        Long updatedBoardId = boardService.update(responseDto.getId(), boardUpdateRequestDto);

        BoardResponseDto resultBoard = boardService.findById(updatedBoardId);

        //then
        assertThat(resultBoard.getTitle()).isEqualTo("updatedTitle");
    }

    @DisplayName("게시글을 삭제한다")
    @Test
    void delete() {
        //given
        BoardResponseDto responseDto = boardService.save(savedLecture.getId(), boardSaveRequestDto);

        //when
        boardService.delete(responseDto.getId());

        BoardsResponseDto boardsResponseDto = boardService.findAll();

        //then
        assertThat(boardsResponseDto.getBoards()).isEmpty();
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
