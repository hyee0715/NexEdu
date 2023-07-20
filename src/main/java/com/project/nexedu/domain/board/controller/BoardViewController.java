package com.project.nexedu.domain.board.controller;

import com.project.nexedu.config.PrincipalDetails;
import com.project.nexedu.domain.board.dto.BoardResponseDto;
import com.project.nexedu.domain.board.dto.BoardsResponseDto;
import com.project.nexedu.domain.board.service.BoardService;
import com.project.nexedu.domain.comment.Comment;
import com.project.nexedu.domain.lecture.dto.LectureResponseDto;
import com.project.nexedu.domain.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/boards")
public class BoardViewController {

    private final BoardService boardService;
    private final LectureService lectureService;

    @GetMapping("/{lectureId}/list")
    public String findByLectureId(@PathVariable("lectureId") Long lectureId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            model.addAttribute("nickname", principalDetails.getUser().getNickname());
        }

        LectureResponseDto lectureResponseDto = lectureService.findById(lectureId);
        model.addAttribute("lecture", lectureResponseDto);

        BoardsResponseDto boardsResponseDto = boardService.findByLectureId(lectureId);
        model.addAttribute("boards", boardsResponseDto);

        return "board/board-list";
    }

    @GetMapping("/detail/{boardId}")
    public String detail(@PathVariable("boardId") Long boardId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            model.addAttribute("nickname", principalDetails.getUser().getNickname());
            model.addAttribute("userId", principalDetails.getUser().getId());
        }

        BoardResponseDto boardResponseDto = boardService.findById(boardId);
        model.addAttribute("board", boardResponseDto);

        List<Comment> comments = boardResponseDto.getComments();
        if (!comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        return "board/board-detail";
    }

    @GetMapping("/new/{lectureId}")
    public String write(@PathVariable("lectureId") Long lectureId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails != null) {
            model.addAttribute("nickname", principalDetails.getUser().getNickname());
            model.addAttribute("userId", principalDetails.getUser().getId());
        }

        model.addAttribute("lectureId", lectureId);

        LectureResponseDto lectureResponseDto = lectureService.findById(lectureId);
        model.addAttribute("lectureTitle", lectureResponseDto.getTitle());

        return "board/board-write";
    }

    @GetMapping("/update/{boardId}")
    public String update(@PathVariable("boardId") Long boardId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails != null) {
            model.addAttribute("nickname", principalDetails.getUser().getNickname());
            model.addAttribute("userId", principalDetails.getUser().getId());
        }

        BoardResponseDto boardResponseDto = boardService.findById(boardId);
        model.addAttribute("board", boardResponseDto);

        return "board/board-update";
    }
}
