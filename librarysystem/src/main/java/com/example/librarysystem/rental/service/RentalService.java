package com.example.librarysystem.rental.service;

import com.example.librarysystem.book.entity.Book;
import com.example.librarysystem.book.repository.BookRepository;
import com.example.librarysystem.rental.entity.Rental;
import com.example.librarysystem.rental.repository.RentalRepository;
import com.example.librarysystem.user.entity.User;
import com.example.librarysystem.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public void rentBook(Long userId, String bookId) {
        if (rentalRepository.findByBook_BookIdAndReturnedAtIsNull(bookId).isPresent()) {
            throw new IllegalStateException("이미 대여 중인 도서입니다.");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("도서가 존재하지 않습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        Rental rental = Rental.builder()
                .book(book)
                .user(user)
                .rentedAt(LocalDateTime.now())
                .build();

        rentalRepository.save(rental);
    }

    @Transactional
    public void returnBook(Long userId, String bookId) {
        Rental rental = rentalRepository.findByBook_BookIdAndReturnedAtIsNull(bookId)
                .orElseThrow(() -> new IllegalArgumentException("대여 중인 도서를 찾을 수 없습니다."));

        if (!rental.getUser().getId().equals(userId)) {
            throw new IllegalStateException("이 도서를 대여한 사용자가 아닙니다.");
        }

        rental.setReturnedAt(LocalDateTime.now());
    }

}
