package com.project.nexedu.domain.board.controller;

import com.project.nexedu.domain.board.dto.BoardResponseDto;
import com.project.nexedu.domain.board.dto.BoardSaveRequestDto;
import com.project.nexedu.domain.board.dto.BoardUpdateRequestDto;
import com.project.nexedu.domain.board.dto.BoardsResponseDto;
import com.project.nexedu.domain.board.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/{lectureId}")
    public ResponseEntity<BoardResponseDto> save(@PathVariable("lectureId") Long lectureId, @RequestBody BoardSaveRequestDto boardSaveRequestDto) {
        BoardResponseDto boardResponseDto = boardService.save(lectureId, boardSaveRequestDto);

        return ResponseEntity.ok(boardResponseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<BoardsResponseDto> list() {
        BoardsResponseDto boardsResponseDto = boardService.findAll();

        return ResponseEntity.ok(boardsResponseDto);
    }

    @GetMapping("/list/{lectureId}")
    public ResponseEntity<BoardsResponseDto> findByLectureId(@PathVariable("lectureId") Long lectureId) {
        BoardsResponseDto boardsResponseDto = boardService.findByLectureId(lectureId);

        return ResponseEntity.ok(boardsResponseDto);
    }

    @GetMapping("/detail/{boardId}")
    public ResponseEntity<BoardResponseDto> detail(@PathVariable("boardId") Long boardId) {
        BoardResponseDto boardResponseDto = boardService.findById(boardId);

        return ResponseEntity.ok(boardResponseDto);
    }

    @PutMapping("/detail/{boardId}")
    public ResponseEntity update(@PathVariable("boardId") Long boardId, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        Long updateId = boardService.update(boardId, boardUpdateRequestDto);

        return ResponseEntity.ok(updateId);
    }

    @DeleteMapping("/detail/{boardId}")
    public ResponseEntity delete(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);

        return ResponseEntity.ok(boardId);
    }
}
