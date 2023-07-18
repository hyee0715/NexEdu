package com.project.nexedu.domain.user.controller;

import com.project.nexedu.domain.user.dto.UserResponseDto;
import com.project.nexedu.domain.user.dto.UserSignUpRequestDto;
import com.project.nexedu.domain.user.dto.UserUpdateRequestDto;
import com.project.nexedu.domain.user.service.UserService;
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

    @PutMapping("/user/update")
    public ResponseEntity update(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        Long updatedUserId = userService.update(userUpdateRequestDto);

        return ResponseEntity.ok("회원 정보 수정 완료, id = " + updatedUserId);
    }

    @GetMapping("/user/detail")
    public ResponseEntity detail() {
        UserResponseDto userResponseDto = userService.detail();

        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity delete() {
        userService.delete();

        return ResponseEntity.ok("회원 삭제 완료");
    }
}