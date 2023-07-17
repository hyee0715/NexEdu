package com.project.nexedu.domain.user;

import com.project.nexedu.domain.Time;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String realName;

    @Column(nullable = false)
    private String username;

    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 1000)
    private String refreshToken;

    @Builder
    public User(Long id, String realName, String username, String password, String nickname, String email, Role role) {
        this.id = id;
        this.realName = realName;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

    public User updateNickname(String nickname) {
        this.nickname = nickname;

        return this;
    }

    public User updatePassword(String password) {
        this.password = password;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public void destroyRefreshToken(){
        this.refreshToken = null;
    }
}