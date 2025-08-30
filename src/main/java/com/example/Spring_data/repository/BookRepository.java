package com.example.Spring_data.repository;

import com.example.Spring_data.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book save(Book book);

    void deleteById(Long id);
}
