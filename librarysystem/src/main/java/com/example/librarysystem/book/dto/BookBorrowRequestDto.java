package com.example.librarysystem.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookBorrowRequestDto {
    private String bookId;
    private String userId;
}
