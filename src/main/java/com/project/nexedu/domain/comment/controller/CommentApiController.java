package com.project.nexedu.domain.comment.controller;

import com.project.nexedu.domain.comment.dto.CommentSaveRequestDto;
import com.project.nexedu.domain.comment.dto.CommentUpdateRequestDto;
import com.project.nexedu.domain.comment.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/board/detail")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comment")
    public ResponseEntity save(@PathVariable Long boardId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {

        return ResponseEntity.ok(commentService.save(commentSaveRequestDto));
    }

    @PutMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity update(@PathVariable Long boardId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto, @PathVariable Long commentId) {
        commentService.update(commentId, commentUpdateRequestDto);
        return ResponseEntity.ok(commentId);
    }

    @DeleteMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity delete(@PathVariable Long boardId, @PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok(commentId);
    }
}
