package com.example.librarysystem.book.controller;

import com.example.librarysystem.book.dto.BookBorrowRequestDto;
import com.example.librarysystem.book.service.BookService;
import com.example.librarysystem.rental.dto.RentalRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestBody RentalRequestDto dto) {
        bookService.borrowBook(dto.getBookId(), dto.getUserId());
        return ResponseEntity.ok("도서 대여 완료");
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody BookBorrowRequestDto dto) {
        bookService.returnBook(dto.getBookId(), dto.getUserId());
        return ResponseEntity.ok("도서 반납 완료");
    }
}
