package com.example.librarysystem.user.controller;

import com.example.librarysystem.user.dto.UserLoginRequestDto;
import com.example.librarysystem.user.dto.UserSignupRequestDto;
import com.example.librarysystem.user.entity.User;
import com.example.librarysystem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto requestDto) {
        User user = userService.login(requestDto);
        return ResponseEntity.ok(user.getName() + "님 로그인 성공!");
    }
}
