package com.project.nexedu.controller;

import com.project.nexedu.domain.dto.user.UserSignUpRequestDto;
import com.project.nexedu.service.UserService;
import com.project.nexedu.validator.CheckNicknameValidator;
import com.project.nexedu.validator.CheckUsernameValidator;
import com.project.nexedu.validator.validation.user.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserApiController {

    private final UserService userService;
    private final CheckUsernameValidator checkUsernameValidator;
    private final CheckNicknameValidator checkNicknameValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkUsernameValidator);
        binder.addValidators(checkNicknameValidator);
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(
            @Validated({RealNameValidationSequence.class, UsernameValidationSequence.class, NicknameValidationSequence.class,
                    PasswordValidationSequence.class, EmailValidationSequence.class}) @RequestBody UserSignUpRequestDto dto) {
        try {
            userService.joinUser(dto);
            return ResponseEntity.ok("join success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}