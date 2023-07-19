package com.project.nexedu.domain.user.controller;

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

import static com.project.nexedu.util.MessageService.showMessageAndRedirect;

@Slf4j
@RequestMapping("/setting")
@RequiredArgsConstructor
@Controller
public class SettingViewController {

    private final UserService userService;
    private final CheckNicknameUpdateValidator checkNicknameUpdateValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkNicknameUpdateValidator);
    }

    @GetMapping("/user/detail")
    public String userDetail(Model model) {
        User user = userService.getCurrentUser();
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(user.getId(), user.getRealName(), user.getNickname(), user.getPassword(), user.getEmail());

        model.addAttribute("userUpdateRequestDto", userUpdateRequestDto);
        model.addAttribute("user", user);

        return "setting/user-detail";
    }

    @PostMapping("/user/update")
    public String userUpdate(@Validated({RealNameValidationSequence.class, NicknameValidationSequence.class,
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

       // return "redirect:/setting/user/detail";
    }
}
