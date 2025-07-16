package com.example.librarysystem.user.entity;

import com.example.librarysystem.book.entity.Book;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchConnectionDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String name;
    private String email;
    private String phone;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "borrowedBy")
    private List<Book> borrowedBooks = new ArrayList<>();
}
