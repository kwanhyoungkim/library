package com.example.librarysystem.user.service;

import ch.qos.logback.classic.encoder.JsonEncoder;
import ch.qos.logback.core.net.SMTPAppenderBase;
import com.example.librarysystem.password.BCryptUtil;
import com.example.librarysystem.password.HashUtil;
import com.example.librarysystem.user.dto.UserLoginRequestDto;
import com.example.librarysystem.user.dto.UserSignupRequestDto;
import com.example.librarysystem.user.entity.User;
import com.example.librarysystem.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public void signup(UserSignupRequestDto requestDto) {
        if (userRepository.existsByUsername(requestDto.getId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        String hashedPassword = BCryptUtil.hashPassword(requestDto.getPassword());

        User user = User.builder()
                .username(requestDto.getId())
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .password(hashedPassword)
                .build();

        userRepository.save(user);
    }

    public User login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!HashUtil.matchPassword(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
