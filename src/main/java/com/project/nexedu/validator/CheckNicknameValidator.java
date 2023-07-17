package com.project.nexedu.validator;

import com.project.nexedu.domain.user.dto.UserSignUpRequestDto;
import com.project.nexedu.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckNicknameValidator extends AbstractValidator<UserSignUpRequestDto> {

    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserSignUpRequestDto userDto, Errors errors) {
        if (userRepository.existsByNickname(userDto.toEntity().getNickname())) {
            errors.rejectValue("nickname","닉네임 중복 오류", "이미 사용 중인 닉네임입니다.");
        }
    }
}
