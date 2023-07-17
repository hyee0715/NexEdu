package com.project.nexedu.config;

import com.project.nexedu.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // User 권한을 리턴하는 메서드
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRoleKey();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() { // User 비밀번호 리턴
        return user.getPassword();
    }

    @Override
    public String getUsername() { // User PK 또는 고유한 값을 리턴
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { // User 계정 만료 여부 리턴
        return true; // true : 만료 안됨
    }

    @Override
    public boolean isAccountNonLocked() { // User 계정 잠김 여부 리턴
        return true; // true : 잠기지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() { // User 비밀번호 만료 여부 리턴
        return true; // true : 만료 안됨
    }

    @Override
    public boolean isEnabled() { // User 계정 활성화 여부 리턴
        return true; // true : 활성화 됨
    }
}
