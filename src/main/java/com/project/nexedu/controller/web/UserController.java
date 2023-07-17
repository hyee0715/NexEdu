package com.project.nexedu.controller.web;

import com.project.nexedu.domain.dto.user.UserSignUpRequestDto;
import com.project.nexedu.service.UserService;
import com.project.nexedu.validator.CheckNicknameValidator;
import com.project.nexedu.validator.CheckUsernameValidator;
import com.project.nexedu.validator.validation.user.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final CheckUsernameValidator checkUsernameValidator;
    private final CheckNicknameValidator checkNicknameValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkUsernameValidator);
        binder.addValidators(checkNicknameValidator);
    }

    @GetMapping("/user/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        HttpServletRequest request, Model model) {

        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/user")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "account/login";
    }

    @GetMapping("/user/signUp")
    public String signUp(Model model) {
        model.addAttribute("userSignUpRequestDto", new UserSignUpRequestDto());
        return "account/signUp";
    }

    @PostMapping("/user/signUp")
    public String signUp(@Validated(
            {RealNameValidationSequence.class, UsernameValidationSequence.class, NicknameValidationSequence.class,
                    PasswordValidationSequence.class, EmailValidationSequence.class}) @ModelAttribute UserSignUpRequestDto userSignUpRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "account/signUp";
        }

        userService.joinUser(userSignUpRequestDto);
        return "redirect:/user/login";
    }
}
