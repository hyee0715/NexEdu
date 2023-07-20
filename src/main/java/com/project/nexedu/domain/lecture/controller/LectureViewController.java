package com.project.nexedu.domain.lecture.controller;

import com.project.nexedu.config.PrincipalDetails;
import com.project.nexedu.domain.lecture.dto.LectureResponseDto;
import com.project.nexedu.domain.lecture.dto.LecturesResponseDto;
import com.project.nexedu.domain.lecture.service.LectureService;
import com.project.nexedu.domain.study.dto.StudyResponseDto;
import com.project.nexedu.domain.study.service.StudyService;
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
    private final StudyService studyService;

    @GetMapping("/")
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            model.addAttribute("nickname", principalDetails.getUser().getNickname());
        }

        LecturesResponseDto lecturesResponseDto = lectureService.findAll();
        model.addAttribute("lectures", lecturesResponseDto);

        return "index";
    }

    @GetMapping("/lectures/detail/{id}")
    public String detail(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if (principalDetails != null) {
            model.addAttribute("nickname", principalDetails.getUser().getNickname());
            model.addAttribute("userId", principalDetails.getUser().getId());

            StudyResponseDto studyResponseDto = studyService.findByLectureIdAndUserId(id, principalDetails.getUser().getId());

            if (studyResponseDto.getId() == null && studyResponseDto.getLecture() == null && studyResponseDto.getUser() == null) {
                model.addAttribute("requested", false);
            }
        }

        LectureResponseDto lectureResponseDto = lectureService.findById(id);
        model.addAttribute("lecture", lectureResponseDto);


        return "lecture/lecture-detail";
    }

    @GetMapping("/lectures/new")
    public String write(Model model) {
        User user = userService.getCurrentUser();

        model.addAttribute("nickname", user.getNickname());
        model.addAttribute("userId", user.getId());

        return "lecture/lecture-write";
    }

    @GetMapping("/lectures/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        User user = userService.getCurrentUser();

        model.addAttribute("nickname", user.getNickname());
        model.addAttribute("userId", user.getId());

        LectureResponseDto lectureResponseDto = lectureService.findById(id);
        model.addAttribute("lecture", lectureResponseDto);

        return "lecture/lecture-update";
    }
}
