package com.project.nexedu.domain.comment.service;

import com.project.nexedu.domain.comment.Comment;
import com.project.nexedu.domain.comment.CommentRepository;
import com.project.nexedu.domain.comment.dto.CommentResponseDto;
import com.project.nexedu.domain.comment.dto.CommentSaveRequestDto;
import com.project.nexedu.domain.comment.dto.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto save(CommentSaveRequestDto commentSaveRequestDto) {
        Comment comment = commentSaveRequestDto.toEntity();
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment.getId(), savedComment.getContent(), savedComment.getBoard(), savedComment.getWriter());
    }

    @Transactional
    public Long update(Long id, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        comment.update(commentUpdateRequestDto.getContent());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));

        commentRepository.delete(comment);
        return id;
    }
}
