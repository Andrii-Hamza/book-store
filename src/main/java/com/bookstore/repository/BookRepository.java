package com.bookstore.repository;

import com.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Book} entities.
 */
@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    /**
     * Finds books by title containing a given keyword (case-insensitive).
     *
     * @param title    the title keyword to search for
     * @return a page of books matching the search criteria
     */
    public Book findByBookTitleIgnoreCase(String title);

    /**
     * Finds books by author containing a given keyword (case-insensitive).
     *
     * @param author   the author keyword to search for
     * @return a page of books matching the search criteria
     */
    public Book findByBookAuthorIgnoreCase(String author);

    /**
     * Finds books by genre containing a given keyword (case-insensitive).
     *
     * @param genre    the genre keyword to search for
     * @return a page of books matching the search criteria
     */
    public Book findByBookGenreIgnoreCase(String genre);
}
