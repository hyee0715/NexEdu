package com.project.nexedu.domain.comment.service;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.board.BoardRepository;
import com.project.nexedu.domain.board.dto.BoardSaveRequestDto;
import com.project.nexedu.domain.comment.Comment;
import com.project.nexedu.domain.comment.CommentRepository;
import com.project.nexedu.domain.comment.dto.CommentResponseDto;
import com.project.nexedu.domain.comment.dto.CommentSaveRequestDto;
import com.project.nexedu.domain.comment.dto.CommentUpdateRequestDto;
import com.project.nexedu.domain.comment.dto.CommentsResponseDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@Transactional
@TestPropertySource(locations = "classpath:/application-test.properties")
@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private CommentService commentService;

    private User savedUser;
    private Lecture savedLecture;
    private Board savedBoard;
    private CommentSaveRequestDto commentSaveRequestDto;

    @BeforeEach
    public void setUp() {
        UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto("testRealName", "testUsername", "testNickname", encodePassword("testPassword"), "testEmail", Role.USER);
        savedUser = userRepository.save(userSignUpRequestDto.toEntity());

        LectureSaveRequestDto lectureSaveRequestDto = new LectureSaveRequestDto("testTitle", savedUser, "testDescription", 10L, savedUser.getId());
        Lecture lecture = lectureSaveRequestDto.toEntity();
        savedLecture = lectureRepository.save(lecture);

        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto();
        boardSaveRequestDto.setTitle("testBoardTitle");
        boardSaveRequestDto.setWriter(savedUser);
        boardSaveRequestDto.setContent("testBoardContent");
        boardSaveRequestDto.setLecture(savedLecture);
        boardSaveRequestDto.setWriterId(savedUser.getId());
        boardSaveRequestDto.setLectureId(savedLecture.getId());
        savedBoard = boardRepository.save(boardSaveRequestDto.toEntity());

        commentSaveRequestDto = new CommentSaveRequestDto();
        commentSaveRequestDto.setWriter(savedUser);
        commentSaveRequestDto.setBoard(savedBoard);
        commentSaveRequestDto.setContent("testCommentContent");
    }

    @DisplayName("댓글을 저장한다")
    @Test
    void save() {
        //given
        //when
        CommentResponseDto commentResponseDto = commentService.save(savedBoard.getId(), commentSaveRequestDto, savedUser);

        //then
        assertThat(commentResponseDto.getContent()).isEqualTo("testCommentContent");
    }

    @DisplayName("댓글을 수정한다")
    @Test
    void update() {
        //given
        CommentResponseDto commentResponseDto = commentService.save(savedBoard.getId(), commentSaveRequestDto, savedUser);

        CommentUpdateRequestDto commentUpdateRequestDto = new CommentUpdateRequestDto();
        commentUpdateRequestDto.setContent("updatedCommentContent");

        //when
        Long updatedCommentId = commentService.update(commentResponseDto.getId(), commentUpdateRequestDto);

        //then
        assertThat(updatedCommentId).isEqualTo(commentResponseDto.getId());
    }

    @DisplayName("댓글을 삭제한다")
    @Test
    void delete() {
        //given
        CommentResponseDto commentResponseDto = commentService.save(savedBoard.getId(), commentSaveRequestDto, savedUser);

        //when
        Long deletedCommentId = commentService.delete(commentResponseDto.getId());

        Optional<Comment> result = commentRepository.findById(deletedCommentId);

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("작성자 id로 댓글을 조회한다")
    @Test
    void findByWriterId() {
        //given
        CommentResponseDto commentResponseDto = commentService.save(savedBoard.getId(), commentSaveRequestDto, savedUser);

        //when
        CommentsResponseDto commentsResponseDto = commentService.findByWriterId(savedUser.getId());

        List<String> commentContents = commentsResponseDto.getComments().stream()
                .map(Comment::getContent).collect(Collectors.toList());

        //then
        assertThat(commentContents).contains(commentResponseDto.getContent());
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}