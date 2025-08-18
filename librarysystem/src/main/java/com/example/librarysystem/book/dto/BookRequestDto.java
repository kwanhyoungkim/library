package com.example.librarysystem.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {
    private String email;    // 유저 이메일
    private String password;
    private String bookId;  // 책 고유 ID
}
