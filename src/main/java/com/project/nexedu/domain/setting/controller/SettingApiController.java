package com.project.nexedu.domain.setting.controller;

import com.project.nexedu.domain.user.dto.UserResponseDto;
import com.project.nexedu.domain.user.dto.UserUpdateRequestDto;
import com.project.nexedu.domain.user.service.UserService;
import com.project.nexedu.validator.CheckNicknameUpdateValidator;
import com.project.nexedu.validator.validation.user.EmailValidationSequence;
import com.project.nexedu.validator.validation.user.NicknameValidationSequence;
import com.project.nexedu.validator.validation.user.PasswordValidationSequence;
import com.project.nexedu.validator.validation.user.RealNameValidationSequence;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class SettingApiController {

    private final UserService userService;
    private final CheckNicknameUpdateValidator checkNicknameUpdateValidator;

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(checkNicknameUpdateValidator);
    }

    @GetMapping("/user/detail")
    public ResponseEntity detail() {
        UserResponseDto userResponseDto = userService.getCurrentUserResponseDto();

        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/user/detail")
    public ResponseEntity update(@Validated({RealNameValidationSequence.class, NicknameValidationSequence.class,
            PasswordValidationSequence.class, EmailValidationSequence.class}) @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        Long updatedUserId = userService.update(userUpdateRequestDto);

        return ResponseEntity.ok(updatedUserId);
    }

    @DeleteMapping("/user/detail/{userId}")
    public ResponseEntity delete(@PathVariable("userId") Long userId) {
        userService.deleteById(userId);

        return ResponseEntity.ok(userId);
    }
}
