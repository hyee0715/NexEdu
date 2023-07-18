package com.project.nexedu.validator;

import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.dto.UserUpdateRequestDto;
import com.project.nexedu.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckNicknameUpdateValidator extends AbstractValidator<UserUpdateRequestDto> {

    private final UserService userService;

    @Override
    protected void doValidate(UserUpdateRequestDto userRequestDto, Errors errors) {

        User currentUser = userService.getCurrentUser();
        String currentNickname = currentUser.getNickname();

        if (!currentNickname.equals(userRequestDto.getNickname()) && userService.checkNicknameExist(userRequestDto.getNickname())) {
            errors.rejectValue("nickname","닉네임 중복 오류", "이미 사용 중인 닉네임입니다.");
        }
    }
}
