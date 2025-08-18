package com.example.librarysystem.book.controller;

import com.example.librarysystem.book.dto.BookRentalRequestDto;
import com.example.librarysystem.book.dto.BookRentalResponseDto;
import com.example.librarysystem.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

   // 전체 도서 목록 조회
    @GetMapping
    public ResponseEntity<List<BookRentalResponseDto>> searchBooksByTitle(@RequestParam String title) {
        List<BookRentalResponseDto> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    // 개별 조회
    @GetMapping("/{bookId}")
    public ResponseEntity<BookRentalResponseDto> getBookById(@PathVariable String bookId) {
        BookRentalResponseDto book = bookService.getBookByBookId(bookId);
        return ResponseEntity.ok(book);
    }

    // 도서 대여
    @PostMapping("/rent")
    public ResponseEntity<BookRentalResponseDto> rentBook(@RequestBody BookRentalRequestDto requestDto) {
        BookRentalResponseDto response = bookService.rentBook(requestDto.getBookId());
        return ResponseEntity.ok(response);
    }

    // 도서 반납
    @PostMapping("/return")
    public ResponseEntity<BookRentalResponseDto> returnBook(@RequestBody BookRentalResponseDto requestDto) {
        BookRentalResponseDto response = bookService.returnBook(requestDto.getBookId());
        return ResponseEntity.ok(response);
    }

    // 책 제목 검색
    @GetMapping("/search")
    public ResponseEntity<List<BookRentalResponseDto>> searchBooks(@RequestParam String title) {
        List<BookRentalResponseDto> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    // 초기화용 도서 등록 (옵션, 외부 API 활용)
    @PostMapping("/initialize")
    public ResponseEntity<String> initializeBooks() {
        bookService.fetchBooksFromOpenApi(); // 외부 API 활용 메서드
        return ResponseEntity.ok("도서 데이터 초기화 완료");
    }
}
