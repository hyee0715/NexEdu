package com.project.nexedu.domain.board.controller;

import com.project.nexedu.domain.board.dto.BoardResponseDto;
import com.project.nexedu.domain.board.dto.BoardSaveRequestDto;
import com.project.nexedu.domain.board.dto.BoardUpdateRequestDto;
import com.project.nexedu.domain.board.dto.BoardsResponseDto;
import com.project.nexedu.domain.board.serivce.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping("/save")
    public ResponseEntity<BoardResponseDto> save(@RequestBody BoardSaveRequestDto boardSaveRequestDto) {
        BoardResponseDto boardResponseDto = boardService.save(boardSaveRequestDto);

        return ResponseEntity.ok(boardResponseDto);
    }

    @GetMapping("/list")
    public ResponseEntity<BoardsResponseDto> list() {
        BoardsResponseDto boardsResponseDto = boardService.findAll();

        return ResponseEntity.ok(boardsResponseDto);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<BoardResponseDto> detail(@PathVariable("id") Long id) {
        BoardResponseDto boardResponseDto = boardService.findById(id);

        return ResponseEntity.ok(boardResponseDto);
    }

    @PutMapping("/detail/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        Long updateId = boardService.update(id, boardUpdateRequestDto);

        return ResponseEntity.ok(updateId);
    }

    @DeleteMapping("/detail/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        boardService.delete(id);

        return ResponseEntity.ok(id);
    }
}
