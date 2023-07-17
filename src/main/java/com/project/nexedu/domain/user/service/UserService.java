package com.project.nexedu.domain.user.service;

import com.project.nexedu.domain.user.dto.UserSignUpRequestDto;
import com.project.nexedu.domain.user.Role;
import com.project.nexedu.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long joinUser(UserSignUpRequestDto userSignUpRequestDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userSignUpRequestDto.setPassword(bCryptPasswordEncoder.encode(userSignUpRequestDto.getPassword()));

        userSignUpRequestDto.setRole(Role.USER);

        return userRepository.save(userSignUpRequestDto.toEntity()).getId();
    }
}