package com.project.nexedu.domain.board.dto;

import com.project.nexedu.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardsResponseDto {

    private List<Board> boards;
}
