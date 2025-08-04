package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book-store")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class BookController {

    @Autowired
    private BookService bookService;

    private final Logger LOGGER =
            LoggerFactory.getLogger(BookController.class);

    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/saveBook")
    public Book saveBook(@Valid @RequestBody Book book) {
        LOGGER.info("Inside saveBook of BookController");
        return bookService.saveBook(book);
    }

    @GetMapping("/booklist")
    public List<Book> fetchBookList() {
        LOGGER.info("Inside fetchBookList of BookController");
        return bookService.fetchBookList();
    }

    @GetMapping("/{id}")
    public Book fetchBookByBookId(@PathVariable("id") Long bookId) {
        return bookService.fetchBookByBookId(bookId);
    }

    @DeleteMapping("/{id}")
    public String deleteBookByBookId(@PathVariable("id") Long bookId) {
        bookService.deleteBookByBookId(bookId);
        return "Book deleted Successfully";
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable("id") Long bookId,
                           @RequestBody Book book) {
        return bookService.updateBook(bookId, book);
    }

    @GetMapping("/title/{title}")
    public Book fetchBookByBookTitle(@PathVariable("title") String bookTitle) {
        return bookService.fetchBookByBookTitle(bookTitle);
    }

    @GetMapping("/author/{author}")
    public Book fetchBookByBookAuthor(@PathVariable("author") String bookAuthor) {
        return bookService.fetchBookByBookAuthor(bookAuthor);
    }

    @GetMapping("/genre/{genre}")
    public Book fetchBookByBookGenre(@PathVariable("genre") String bookGenre) {
        return bookService.fetchBookByBookGenre(bookGenre);
    }


    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the unprotected page!";
    }

}
