package com.example.librarysystem.book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @Column(nullable = false, unique = true)
    private String bookId; // 외부 API의 LEND_NO

    private String title;

    private String author;

    private boolean isAvailable; // 대여 가능 여부

    @CreationTimestamp
    private LocalDateTime rentedAt;

    @UpdateTimestamp
    private LocalDateTime dueDate;
}
