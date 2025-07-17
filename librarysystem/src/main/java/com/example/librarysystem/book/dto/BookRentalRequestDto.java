package com.example.librarysystem.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRentalRequestDto {
    private Long userId;    // 유저 고유 ID
    private String bookId;  // 책 고유 ID
}
