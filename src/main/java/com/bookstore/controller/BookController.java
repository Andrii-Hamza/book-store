package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping
    public Book saveBook(@Valid @RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @GetMapping
    public List<Book> fetchBookList() {
        return bookService.fetchBookList();
    }

    @GetMapping("/{id}")
    public Book fetchBookById(@PathVariable("id") Long bookId) {
        return bookService.fetchBookById(bookId);
    }

    @DeleteMapping("/{id}")
    public String  deleteBookById(@PathVariable("id") Long bookId) {
        bookService.deleteBookById(bookId);
        return "Book deleted Successfully";
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable("id") Long bookId,
                           @RequestBody Book book) {
        return bookService.updateBook(bookId, book);
    }

    @GetMapping("/title/{title}")
    public Book fetchBookByTitle(@PathVariable("title") String bookTitle) {
        return bookService.fetchBookByTitle(bookTitle);
    }

}
