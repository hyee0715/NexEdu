package com.project.nexedu.domain.user.service;

import com.project.nexedu.domain.study.StudyRepository;
import com.project.nexedu.domain.user.User;
import com.project.nexedu.domain.user.dto.UserResponseDto;
import com.project.nexedu.domain.user.dto.UserSignUpRequestDto;
import com.project.nexedu.domain.user.Role;
import com.project.nexedu.domain.user.UserRepository;
import com.project.nexedu.domain.user.dto.UserUpdateRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final StudyRepository studyRepository;

    @Transactional
    public Long joinUser(UserSignUpRequestDto userSignUpRequestDto) {
        userSignUpRequestDto.setPassword(encodePassword(userSignUpRequestDto.getPassword()));

        userSignUpRequestDto.setRole(Role.USER);

        return userRepository.save(userSignUpRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(UserUpdateRequestDto userUpdateRequestDto) {
        User user = getCurrentUser();

        user.update(userUpdateRequestDto.getRealName(), userUpdateRequestDto.getNickname(), encodePassword(userUpdateRequestDto.getPassword()), userUpdateRequestDto.getEmail());

        return user.getId();
    }

    public UserResponseDto detail() {
        User user = getCurrentUser();

        return new UserResponseDto(user.getId(), user.getRealName(), user.getUsername(), user.getPassword(), user.getNickname(), user.getEmail(), user.getRole());
    }

    @Transactional
    public void delete() {
        User user = getCurrentUser();

        studyRepository.deleteByUser(user);
        userRepository.delete(user);
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    private User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }
}