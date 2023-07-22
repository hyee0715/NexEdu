package com.project.nexedu.domain.comment.service;

import com.project.nexedu.domain.board.Board;
import com.project.nexedu.domain.board.BoardRepository;
import com.project.nexedu.domain.comment.Comment;
import com.project.nexedu.domain.comment.CommentRepository;
import com.project.nexedu.domain.comment.dto.CommentResponseDto;
import com.project.nexedu.domain.comment.dto.CommentSaveRequestDto;
import com.project.nexedu.domain.comment.dto.CommentUpdateRequestDto;
import com.project.nexedu.domain.comment.dto.CommentsResponseDto;
import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto save(Long boardId, CommentSaveRequestDto commentSaveRequestDto, User writer) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        commentSaveRequestDto.setBoard(board);
        commentSaveRequestDto.setWriter(writer);

        Comment comment = commentSaveRequestDto.toEntity();
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    public CommentResponseDto findById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        return new CommentResponseDto(comment);
    }

    @Transactional
    public Long update(Long id, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        comment.update(commentUpdateRequestDto.getContent());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        commentRepository.delete(comment);
        return id;
    }

    public CommentsResponseDto findByWriterId(Long writerId) {
        User user = userRepository.findById(writerId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        List<Comment> comments = commentRepository.findByWriterOrderByIdDesc(user);

        return new CommentsResponseDto(comments);
    }
}
