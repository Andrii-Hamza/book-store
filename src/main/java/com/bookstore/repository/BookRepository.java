package com.bookstore.repository;

import com.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {


    public Book findByBookTitle(String title);

    //@Query(value = "", nativeQuery = true)
    public Book findByBookTitleIgnoreCase(String title);

    public Book findByBookAuthorIgnoreCase(String author);

    public Book findByBookGenreIgnoreCase(String genre);
}
