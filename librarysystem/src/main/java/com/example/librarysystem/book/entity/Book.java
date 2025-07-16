package com.example.librarysystem.book.entity;

import com.example.librarysystem.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Book {
    @Id
    private String bookId;
    private String title;
    private String author;
    @Column(nullable = false)
    private boolean isBorrowed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrowed_by_user_id")
    private User borrowedBy;

    private Boolean isAvailable;
}
