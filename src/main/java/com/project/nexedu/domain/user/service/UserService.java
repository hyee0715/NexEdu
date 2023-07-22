package com.project.nexedu.domain.user.service;

import com.project.nexedu.domain.board.BoardRepository;
import com.project.nexedu.domain.comment.CommentRepository;
import com.project.nexedu.domain.lecture.LectureRepository;
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
    private final BoardRepository boardRepository;
    private final LectureRepository lectureRepository;
    private final CommentRepository commentRepository;

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

    @Transactional
    public void deleteById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        commentRepository.deleteByWriter(user);
        studyRepository.deleteByUser(user);
        boardRepository.deleteByWriter(user);
        lectureRepository.deleteByInstructor(user);
        userRepository.delete(user);
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public UserResponseDto getCurrentUserResponseDto() {
        User user = getCurrentUser();

        return new UserResponseDto(user);
    }

    public User getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }

    public boolean checkNicknameExist(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}