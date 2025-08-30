package com.example.Spring_data.repository;

import com.example.Spring_data.component.BookRowMapper;
import com.example.Spring_data.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JdbcBookRepository implements BookRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BookRowMapper bookRowMapper;

    @Override
    public List<Book> findAll() {
        String sql = "select * from book";
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "select * from book where id = ?";
        List<Book> books = jdbcTemplate.query(sql, bookRowMapper, id);
        return books.stream().findFirst();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            String sql = "INSERT INTO books (title, author, publication_year) VALUES (?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connecton-> {
                PreparedStatement ps = connecton.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, book.getTitle());
                ps.setString(2, book.getAuthor());
                ps.setInt(3, book.getPublicationYear());
                return ps;
            },keyHolder);
            book.setId(keyHolder.getKey().longValue());
            return book;

        }else {
            String sql = "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?";
            jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getId());
            return book;
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }
}
