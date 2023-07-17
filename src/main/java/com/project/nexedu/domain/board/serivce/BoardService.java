package com.project.nexedu.domain.board.serivce;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.board.BoardRepository;
import com.project.nexedu.domain.board.dto.BoardResponseDto;
import com.project.nexedu.domain.board.dto.BoardSaveRequestDto;
import com.project.nexedu.domain.board.dto.BoardUpdateRequestDto;
import com.project.nexedu.domain.board.dto.BoardsResponseDto;
import com.project.nexedu.domain.lecture.Lecture;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponseDto save(BoardSaveRequestDto boardSaveRequestDto) {
        Board board = boardSaveRequestDto.toEntity();
        Board savedBoard = boardRepository.save(board);

        return new BoardResponseDto(savedBoard.getId(), savedBoard.getTitle(), savedBoard.getWriter(), savedBoard.getContent(), savedBoard.getLecture());
    }

    public BoardsResponseDto findAll() {
        List<Board> boards = boardRepository.findAll();

        return new BoardsResponseDto(boards);
    }

    public BoardResponseDto findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);

        return new BoardResponseDto(board.getId(), board.getTitle(), board.getWriter(), board.getContent(), board.getLecture());
    }

    public BoardsResponseDto findByLecture(Lecture lecture) {
        List<Board> boards = boardRepository.findByLecture(lecture);

        return new BoardsResponseDto(boards);
    }

    @Transactional
    public Long update(Long id, BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);

        board.update(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);

        boardRepository.delete(board);
        return id;
    }
}
