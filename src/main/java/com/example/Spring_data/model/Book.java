package com.example.Spring_data.model;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "book")
public class Book {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
}
