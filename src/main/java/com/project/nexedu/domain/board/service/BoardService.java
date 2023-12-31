package com.project.nexedu.domain.board.service;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.board.BoardRepository;
import com.project.nexedu.domain.board.dto.BoardResponseDto;
import com.project.nexedu.domain.board.dto.BoardSaveRequestDto;
import com.project.nexedu.domain.board.dto.BoardUpdateRequestDto;
import com.project.nexedu.domain.board.dto.BoardsResponseDto;
import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.lecture.LectureRepository;
import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponseDto save(Long lectureId, BoardSaveRequestDto boardSaveRequestDto) {
        User writer = userRepository.findById(boardSaveRequestDto.getWriterId()).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        boardSaveRequestDto.setWriter(writer);

        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));
        boardSaveRequestDto.setLecture(lecture);

        Board board = boardSaveRequestDto.toEntity();
        Board savedBoard = boardRepository.save(board);

        return new BoardResponseDto(savedBoard);
    }

    public BoardsResponseDto findAll() {
        List<Board> boards = boardRepository.findAllByOrderByIdDesc();

        return new BoardsResponseDto(boards);
    }

    public BoardResponseDto findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return new BoardResponseDto(board);
    }

    public BoardsResponseDto findByLectureId(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new IllegalArgumentException("강의가 존재하지 않습니다."));
        List<Board> boards = boardRepository.findByLectureOrderByIdDesc(lecture);

        return new BoardsResponseDto(boards);
    }

    public BoardsResponseDto findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        List<Board> boards = boardRepository.findByWriterOrderByIdDesc(user);

        return new BoardsResponseDto(boards);
    }

    @Transactional
    public Long update(Long id, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        board.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boardRepository.delete(board);
        return id;
    }
}
