package com.project.nexedu.config;

import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntityWrapper = userRepository.findByUsername(username);
        User userEntity = userEntityWrapper.orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));

        return new PrincipalDetails(userEntity);
    }
}