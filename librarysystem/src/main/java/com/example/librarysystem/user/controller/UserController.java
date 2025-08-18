package com.example.librarysystem.user.controller;

import com.example.librarysystem.user.dto.UserDeleteRequestDto;
import com.example.librarysystem.user.dto.UserLoginRequestDto;
import com.example.librarysystem.user.dto.UserSignupRequestDto;
import com.example.librarysystem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto loginDto) {
        userService.login(loginDto);
        return  ResponseEntity.ok("로그인 되었습니다.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody UserDeleteRequestDto deleteDto){
        userService.deleteUser(deleteDto);
        return  ResponseEntity.ok("회원탈퇴가 완료되었습니다.");
    }

}

