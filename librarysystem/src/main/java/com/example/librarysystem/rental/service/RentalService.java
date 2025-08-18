/*
package com.example.librarysystem.rental.service;

import com.example.librarysystem.book.entity.Book;
import com.example.librarysystem.book.repository.BookRepository;
import com.example.librarysystem.password.PasswordUtil;
import com.example.librarysystem.rental.entity.Rental;
import com.example.librarysystem.rental.repository.RentalRepository;
import com.example.librarysystem.user.entity.User;
import com.example.librarysystem.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public void rentBook(String email, String password, String bookId) {
        if (rentalRepository.findByBook_BookIdAndReturnedAtIsNull(bookId).isPresent()) {
            throw new IllegalStateException("이미 대여 중인 도서입니다.");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("도서가 존재하지 않습니다."));
        User user = userRepository.findByEmail(email)
               .orElseThrow(() -> new IllegalArgumentException("도서가 존재하지 않습니다."));

        // 비밀번호 확인
        if (!PasswordUtil.matchPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Rental rental = Rental.builder()
                .user(user)
                .book(book)
                .rentedAt(LocalDateTime.now())
                .build();

        rentalRepository.save(rental);
    }

    @Transactional
    public void returnBook(String email, String password, String bookId) {
        Rental rental = rentalRepository.findByBook_BookIdAndReturnedAtIsNull(bookId)
                .orElseThrow(() -> new IllegalArgumentException("대여 중인 도서를 찾을 수 없습니다."));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        // 2. 비밀번호 일치 확인
        if (!PasswordUtil.matchPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 도서 조회
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("도서가 존재하지 않습니다."));

        // 4. 대여 여부 확인
        if (!book.isBorrowed()) {
            throw new IllegalStateException("이 도서는 대여되지 않았습니다.");
        }

        if (!rental.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("이 도서를 대여한 사용자가 아닙니다.");
        }

        // 6. 반납 처리
        book.setBorrowed(false);
        book.setBorrowedBy(null);
        bookRepository.save(book);

        rental.setReturnedAt(LocalDateTime.now());
    }

}
*/
