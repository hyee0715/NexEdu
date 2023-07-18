package com.project.nexedu.domain.lecture.controller;

import com.project.nexedu.config.PrincipalDetails;
import com.project.nexedu.domain.lecture.dto.LectureResponseDto;
import com.project.nexedu.domain.lecture.dto.LecturesResponseDto;
import com.project.nexedu.domain.lecture.service.LectureService;
import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LectureViewController {

    private final LectureService lectureService;
    private final UserService userService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            model.addAttribute("nickname", principalDetails.getUser().getNickname());
        }

        LecturesResponseDto lecturesResponseDto = lectureService.findAll();
        model.addAttribute("lectures", lecturesResponseDto);

        return "index";
    }

    @GetMapping("/lecture/detail/{id}")
    public String detail(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            model.addAttribute("nickname", principalDetails.getUser().getNickname());
            model.addAttribute("userId", principalDetails.getUser().getId());
        }

        LectureResponseDto lectureResponseDto = lectureService.findById(id);
        model.addAttribute("lecture", lectureResponseDto);

        return "lecture/lecture-detail";
    }

    @GetMapping("/lecture/write")
    public String write(Model model) {
        User user = userService.getCurrentUser();

        model.addAttribute("nickname", user.getNickname());
        model.addAttribute("userId", user.getId());

        return "lecture/lecture-write";
    }

    @GetMapping("/lecture/detail/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        User user = userService.getCurrentUser();

        model.addAttribute("nickname", user.getNickname());
        model.addAttribute("userId", user.getId());

        LectureResponseDto lectureResponseDto = lectureService.findById(id);
        model.addAttribute("lecture", lectureResponseDto);

        return "lecture/lecture-update";
    }
}
