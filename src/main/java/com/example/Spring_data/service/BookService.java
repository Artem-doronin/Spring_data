package com.example.Spring_data.service;

import com.example.Spring_data.model.Book;
import com.example.Spring_data.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository repository;

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return repository.findById(id);
    }

    public Book createBook(Book book) {
        book.setId(null);
        return repository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book book) {
        return repository.findById(id).map(existing -> {
            existing.setTitle(book.getTitle());
            existing.setAuthor(book.getAuthor());
            existing.setPublicationYear(book.getPublicationYear());
            return repository.save(existing);
        });
    }

    public boolean deleteBook(Long id) {
        return repository.findById(id).map(book -> {
            repository.deleteById(id);
            return true;
        }).orElse(false);
    }
}
