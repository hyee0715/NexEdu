package com.project.nexedu.domain.comment.dto;

import com.project.nexedu.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CommentsResponseDto {

    List<Comment> comments;
}
