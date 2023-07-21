package com.project.nexedu.domain.user.dto;

import com.project.nexedu.domain.user.Role;
import com.project.nexedu.domain.user.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String realName;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.realName = user.getRealName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
