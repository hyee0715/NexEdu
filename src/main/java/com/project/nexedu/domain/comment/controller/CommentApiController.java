package com.project.nexedu.domain.comment.controller;

import com.project.nexedu.domain.comment.dto.CommentResponseDto;
import com.project.nexedu.domain.comment.dto.CommentSaveRequestDto;
import com.project.nexedu.domain.comment.dto.CommentUpdateRequestDto;
import com.project.nexedu.domain.comment.service.CommentService;
import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/board/detail")
public class CommentApiController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/{boardId}/comment")
    public ResponseEntity save(@PathVariable Long boardId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        User user = userService.getCurrentUser();
        CommentResponseDto commentResponseDto = commentService.save(boardId, commentSaveRequestDto, user);
        return ResponseEntity.ok(commentResponseDto.getId());
    }

    @PutMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity update(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        commentService.update(commentId, commentUpdateRequestDto);
        return ResponseEntity.ok(commentId);
    }

    @DeleteMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity delete(@PathVariable Long boardId, @PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok(commentId);
    }
}
