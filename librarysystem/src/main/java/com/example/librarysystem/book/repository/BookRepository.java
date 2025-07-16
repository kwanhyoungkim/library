package com.example.librarysystem.book.repository;

import com.example.librarysystem.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findByBookId(String bookId);
    boolean existsByBookId(String bookId);
}
