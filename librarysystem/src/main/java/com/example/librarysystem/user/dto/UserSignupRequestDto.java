package com.example.librarysystem.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupRequestDto {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
}
