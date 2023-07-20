package com.project.nexedu.domain.setting.controller;

import com.project.nexedu.domain.board.dto.BoardsResponseDto;
import com.project.nexedu.domain.board.service.BoardService;
import com.project.nexedu.domain.comment.dto.CommentsResponseDto;
import com.project.nexedu.domain.comment.service.CommentService;
import com.project.nexedu.domain.lecture.Lecture;
import com.project.nexedu.domain.lecture.dto.LecturesResponseDto;
import com.project.nexedu.domain.lecture.service.LectureService;
import com.project.nexedu.domain.study.Study;
import com.project.nexedu.domain.study.dto.StudiesResponseDto;
import com.project.nexedu.domain.study.service.StudyService;
import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.dto.UserResponseDto;
import com.project.nexedu.domain.user.dto.UserUpdateRequestDto;
import com.project.nexedu.domain.user.service.UserService;
import com.project.nexedu.util.MessageDto;
import com.project.nexedu.validator.CheckNicknameUpdateValidator;
import com.project.nexedu.validator.validation.user.EmailValidationSequence;
import com.project.nexedu.validator.validation.user.NicknameValidationSequence;
import com.project.nexedu.validator.validation.user.PasswordValidationSequence;
import com.project.nexedu.validator.validation.user.RealNameValidationSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.nexedu.util.MessageService.showMessageAndRedirect;

@Slf4j
@RequestMapping("/setting")
@RequiredArgsConstructor
@Controller
public class SettingViewController {

    private final UserService userService;
    private final CheckNicknameUpdateValidator checkNicknameUpdateValidator;
    private final StudyService studyService;
    private final BoardService boardService;
    private final LectureService lectureService;
    private final CommentService commentService;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkNicknameUpdateValidator);
    }

    @GetMapping("/user/detail")
    public String getUserDetail(Model model) {
        User user = userService.getCurrentUser();
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(user.getId(), user.getRealName(), user.getNickname(), user.getPassword(), user.getEmail());

        model.addAttribute("userUpdateRequestDto", userUpdateRequestDto);
        model.addAttribute("user", user);

        return "setting/user-detail";
    }

    @PostMapping("/user/update")
    public String updateUser(@Validated({RealNameValidationSequence.class, NicknameValidationSequence.class,
            PasswordValidationSequence.class, EmailValidationSequence.class}) @ModelAttribute UserUpdateRequestDto userUpdateRequestDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("errors ={}", bindingResult);

            UserResponseDto userResponseDto = userService.getCurrentUserResponseDto();
            model.addAttribute("user", userResponseDto);

            return "setting/user-detail";
        }

        userService.update(userUpdateRequestDto);

        MessageDto message = new MessageDto("회원 정보 변경이 완료되었습니다.", "/setting/user/detail", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    @GetMapping("/lectures")
    public String getStudyLectures(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("nickname", user.getNickname());

        StudiesResponseDto studiesResponseDto = studyService.findByUserId(user.getId());
        List<Lecture> lectures = studiesResponseDto.getStudies().stream()
                .map(Study::getLecture)
                .toList();

        model.addAttribute("lectures", lectures);

        return "setting/study-lectures";
    }

    @PostMapping("/lectures/delete")
    public String deleteStudies(@RequestParam List<String> lectureIds) {
        lectureIds.stream()
                .map(Long::valueOf)
                .forEach(studyService::deleteByLectureId);

        return "redirect:/setting/lectures";
    }

    @GetMapping("/boards")
    public String getUserBoards(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("nickname", user.getNickname());

        BoardsResponseDto boardsResponseDto = boardService.findByUserId(user.getId());
        model.addAttribute("boards", boardsResponseDto);

        return "setting/user-boards";
    }

    @PostMapping("/boards/delete")
    public String deleteBoards(@RequestParam List<String> boardIds) {
        boardIds.stream()
                .map(Long::valueOf)
                .forEach(boardService::delete);

        return "redirect:/setting/boards";
    }

    @GetMapping("/comments")
    public String getUserComments(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("nickname", user.getNickname());

        CommentsResponseDto commentsResponseDto = commentService.findByWriterId(user.getId());
        model.addAttribute("comments", commentsResponseDto);

        return "setting/user-comments";
    }

    @PostMapping("/comments/delete")
    public String deleteComments(@RequestParam List<String> commentIds) {
        commentIds.stream()
                .map(Long::valueOf)
                .forEach(commentService::delete);

        return "redirect:/setting/comments";
    }

    @GetMapping("/upload-lectures")
    public String getUploadLectures(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("nickname", user.getNickname());

        LecturesResponseDto lecturesResponseDto = lectureService.findByInstructorId(user.getId());
        model.addAttribute("lectures", lecturesResponseDto);

        return "setting/upload-lectures";
    }

    @PostMapping("/upload-lectures/delete")
    public String deleteUploadLectures(@RequestParam List<String> lectureIds) {
        lectureIds.stream()
                .map(Long::valueOf)
                .forEach(lectureService::delete);

        return "redirect:/setting/upload-lectures";
    }

    @GetMapping("/withdrawal")
    public String withdraw(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("nickname", user.getNickname());
        model.addAttribute("userId", user.getId());

        return "setting/user-withdrawal";
    }
}
