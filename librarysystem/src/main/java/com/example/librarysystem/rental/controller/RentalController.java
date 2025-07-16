package com.example.librarysystem.rental.controller;

import com.example.librarysystem.rental.dto.RentalRequestDto;
import com.example.librarysystem.rental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rental")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @PostMapping("/borrow")
    public ResponseEntity<String> borrow(@RequestBody RentalRequestDto dto) {
        rentalService.rentBook(dto.getUserId(), dto.getBookId());
        return ResponseEntity.ok("도서 대여 성공");
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody RentalRequestDto dto) {
        rentalService.returnBook(dto.getUserId(), dto.getBookId());
        return ResponseEntity.ok("도서 반납 성공");
    }
}
