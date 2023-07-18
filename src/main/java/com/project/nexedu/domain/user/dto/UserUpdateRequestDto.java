package com.project.nexedu.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String realName;
    private String nickname;
    private String password;
    private String email;
}
