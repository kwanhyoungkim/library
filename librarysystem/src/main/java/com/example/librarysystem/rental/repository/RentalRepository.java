package com.example.librarysystem.rental.repository;

import com.example.librarysystem.rental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    Optional<Rental> findByBook_BookIdAndReturnedAtIsNull(String bookId);
}
