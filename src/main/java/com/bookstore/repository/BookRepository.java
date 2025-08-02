package com.bookstore.repository;

import com.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    public Book findByBookTitle(String bookTitle);

    //@Query(value = "", nativeQuery = true)
    public Book findByBookTitleIgnoreCase(String bookTitle);

    public Book findByBookAuthorIgnoreCase(String bookAuthor);

    public Book findByBookGenreIgnoreCase(String bookGenre);
}
