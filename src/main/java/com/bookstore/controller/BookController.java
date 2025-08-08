package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller for managing books.
 * Provides endpoints for getting, creating, updating, deleting, and searching books.
 */
@RestController
@RequestMapping("/api/books")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Gets all books.
     * Accessible only to authorized users.
     *
     * @return List containing a list of Books.
     */
    @GetMapping("/booklist")
    @PreAuthorize("isAuthenticated()")
    public List<Book> fetchBookList() {
        return bookService.fetchBookList();
    }

    /**
     * Gets a book by its ID.
     * Accessible only to authorized users.
     *
     * @param bookId the ID of the book
     * @return The Book containing the Book if found.
     */
    @GetMapping("/booklist/{id}")
    @PreAuthorize("isAuthenticated()")
    public Book fetchBookByBookId(@PathVariable("id") Long bookId) {
        return bookService.fetchBookByBookId(bookId);
    }

    /**
     * Creates a new book.
     * Accessible only to users with the ADMIN role.
     *
     * @param book the Book containing book information
     * @return Method containing the created Book and message to user.
     */
    @PostMapping("/saveBook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String saveBook(@Valid @RequestBody Book book) {
        bookService.saveBook(book);
        return "Book saved successfully";
    }

    /**
     * Deletes a book by its ID.
     * Accessible only to users with the ADMIN role.
     *
     * @param bookId the ID of the book to delete
     * @return Deleting the book by id and getting the message to user.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteBookByBookId(@PathVariable("id") Long bookId) {
        bookService.deleteBookByBookId(bookId);
        return "Book deleted Successfully";
    }

    /**
     * Updating a book by its ID.
     * Accessible only to users with the ADMIN role.
     *
     * @param bookId the ID of the book to update
     * @param book information about the book.
     * @return Updating the book by id and getting the message to user.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateBook(@PathVariable("id") Long bookId,
                              @RequestBody Book book) {
        bookService.updateBook(bookId, book);
        return "Book updated successfully";
    }

    /**
     * Searches for books based on title.
     * Accessible only to authorized users.
     *
     * @param bookTitle the title keyword (optional)
     * @return Book containing information about it.
     */
    @GetMapping("/title/{title}")
    @PreAuthorize("isAuthenticated()")
    public Book fetchBookByBookTitle(@PathVariable("title") String bookTitle) {
        return bookService.fetchBookByBookTitle(bookTitle);
    }

    /**
     * Searches for books based on author.
     * Accessible only to authorized users.
     *
     * @param bookAuthor the author keyword (optional)
     * @return Book containing information about it.
     */
    @GetMapping("/author/{author}")
    @PreAuthorize("isAuthenticated()")
    public Book fetchBookByBookAuthor(@PathVariable("author") String bookAuthor) {
        return bookService.fetchBookByBookAuthor(bookAuthor);
    }

    /**
     * Searches for books based on genre.
     * Accessible only to authorized users.
     *
     * @param bookGenre the genre keyword (optional)
     * @return Book containing information about it.
     */
    @GetMapping("/genre/{genre}")
    @PreAuthorize("isAuthenticated()")
    public Book fetchBookByBookGenre(@PathVariable("genre") String bookGenre) {
        return bookService.fetchBookByBookGenre(bookGenre);
    }

}

