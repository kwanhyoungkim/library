package com.example.librarysystem.user.service;

import com.example.librarysystem.password.PasswordUtil;
import com.example.librarysystem.user.dto.*;
import com.example.librarysystem.user.entity.User;
import com.example.librarysystem.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            return new UserSignupResponseDto(false,"이미 사용중인 이메일입니다.",null);
        }

        String hashed = PasswordUtil.hash(requestDto.getPassword());

        User user = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .password(hashed)
                .build();

        userRepository.save(user);
        return new UserSignupResponseDto(true, "회원가입 성공", user.getEmail());
    }

    // 로그인
    public UserLoginResponseDto login(UserLoginRequestDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
               // .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
                .orElse(null);
        if (user == null) {
            return new UserLoginResponseDto(false, "존재하지 않는 이메일입니다.", null);
        }

        // 2. 비밀번호 비교
        if (!PasswordUtil.matchPassword(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new UserLoginResponseDto(true, "로그인 성공", user.getEmail());
    }

    // 유저삭제
    public UserDeleteResponseDto deleteUser(UserDeleteRequestDto deleteDto) {
        // 1. 이메일로 사용자 조회
        Optional<User> userOptional = userRepository.findByEmail(deleteDto.getEmail());

        if (userOptional.isEmpty()) {
            return new UserDeleteResponseDto(false,"해당 이메일로 등록된 계정을 찾을 수 없습니다.");
        }

        User user = userOptional.get();

        // 2. 비밀번호 확인
        if (!PasswordUtil.matchPassword(deleteDto.getPassword(), user.getPassword())) {
            return new UserDeleteResponseDto(false,"비밀번호가 일치하지 않습니다.");
        }

        // 3. 사용자 삭제
        userRepository.delete(user);
        // 4. 삭제 완료 응답 반환
        return new UserDeleteResponseDto(true,"탈퇴되었습니다");
    }
}