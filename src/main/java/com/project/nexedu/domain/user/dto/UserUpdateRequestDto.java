package com.project.nexedu.domain.user.dto;

import com.project.nexedu.validator.validation.user.EmailValidationGroups;
import com.project.nexedu.validator.validation.user.NicknameValidationGroups;
import com.project.nexedu.validator.validation.user.PasswordValidationGroups;
import com.project.nexedu.validator.validation.user.RealNameValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {

    @Size(min = 2, max = 6, message = "이름은 2~6자리 제한입니다.", groups = RealNameValidationGroups.SizeCheckGroup.class)
    @NotBlank(groups = RealNameValidationGroups.NotNullGroup.class)
    @Pattern(regexp = "^[가-힣]{2,6}$", message = "이름은 한글로 구성되어야 합니다.", groups = RealNameValidationGroups.PatternCheckGroup.class)
    private String realName;

    @Size(min = 2, max = 10, message = "닉네임은 2~10자리 제한입니다.", groups = NicknameValidationGroups.SizeCheckGroup.class)
    @NotBlank(groups = NicknameValidationGroups.NotNullGroup.class)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "닉네임은 특수문자가 포함될 수 없습니다.", groups = NicknameValidationGroups.PatternCheckGroup.class)
    private String nickname;

    @Size(min = 8, max = 20, message = "비밀번호는 8~20자리 제한입니다.", groups = PasswordValidationGroups.SizeCheckGroup.class)
    @NotBlank(groups = PasswordValidationGroups.NotNullGroup.class)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 영문 대소문자, 숫자, 특수기호 1개 이상으로 구성되어야 합니다.", groups = PasswordValidationGroups.PatternCheckGroup.class)
    private String password;

    @Email(groups = EmailValidationGroups.EmailCheckGroup.class)
    @NotBlank(groups = EmailValidationGroups.NotNullGroup.class)
    private String email;
}
