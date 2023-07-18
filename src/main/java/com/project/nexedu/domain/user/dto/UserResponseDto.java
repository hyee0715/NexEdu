package com.project.nexedu.domain.user.dto;

import com.project.nexedu.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String realName;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    @Builder
    public UserResponseDto(Long id, String realName, String username, String password, String nickname, String email, Role role) {
        this.id = id;
        this.realName = realName;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }
}
