package com.example.librarysystem.rental.entity;

import com.example.librarysystem.book.entity.Book;
import com.example.librarysystem.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    private LocalDateTime rentedAt;

    private LocalDateTime returnedAt;

    public boolean isReturned() {
        return returnedAt != null;
    }
}
