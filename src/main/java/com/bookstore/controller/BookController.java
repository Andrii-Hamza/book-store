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

@RestController
@RequestMapping("/api/books")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping("/booklist")
    @PreAuthorize("isAuthenticated()")
    public List<Book> fetchBookList() {
        return bookService.fetchBookList();
    }

    @GetMapping("/booklist/{id}")
    @PreAuthorize("isAuthenticated()")
    public Book fetchBookByBookId(@PathVariable("id") Long bookId) {
        return bookService.fetchBookByBookId(bookId);
    }

    @PostMapping("/saveBook")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String saveBook(@Valid @RequestBody Book book) {
        bookService.saveBook(book);
        return "Book saved successfully";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteBookByBookId(@PathVariable("id") Long bookId) {
        bookService.deleteBookByBookId(bookId);
        return "Book deleted Successfully";
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateBook(@PathVariable("id") Long bookId,
                              @RequestBody Book book) {
        bookService.updateBook(bookId, book);
        return "Book updated successfully";
    }

    @GetMapping("/title/{title}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Book fetchBookByBookTitle(@PathVariable("title") String bookTitle) {
        return bookService.fetchBookByBookTitle(bookTitle);
    }

    @GetMapping("/author/{author}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Book fetchBookByBookAuthor(@PathVariable("author") String bookAuthor) {
        return bookService.fetchBookByBookAuthor(bookAuthor);
    }

    @GetMapping("/genre/{genre}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Book fetchBookByBookGenre(@PathVariable("genre") String bookGenre) {
        return bookService.fetchBookByBookGenre(bookGenre);
    }

}

