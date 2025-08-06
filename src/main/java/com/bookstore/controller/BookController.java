package com.bookstore.controller;

import com.bookstore.entity.Book;
import com.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/api/books")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private BookService bookService;

    private final Logger LOGGER =
            LoggerFactory.getLogger(BookController.class);

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





    @GetMapping("/")
    public String greet(HttpServletRequest request) {
        return "Welcome to the unprotected page!" + request.getSession().getId();
    }

//    private List<Book> books = new ArrayList<Book>(List.of(
//            new Book(1L, "harry", "clowm", "1213", 3.2),
//            new Book(2L, "toss", "clowm", "1213", 3.4)
//    ));
//
//    @GetMapping("/books")
//    public List<Book> getBooks() {
//        return books;
//    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.fetchBookList();
    }

    @PostMapping("/books")
    public Book addBook(@Valid @RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }


}
