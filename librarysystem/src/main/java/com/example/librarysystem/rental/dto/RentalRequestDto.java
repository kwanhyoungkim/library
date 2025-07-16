package com.example.librarysystem.rental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalRequestDto {
    private Long userId;
    private String bookId;

}
