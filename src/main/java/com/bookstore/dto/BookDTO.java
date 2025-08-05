package com.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private Long id;
    //@NotBlank(message = "Please add Book Name")
    private String title;
    private String author;
    private String genre;
    private double price;
    private String createdAt;
    private String updatedAt;
}
