package com.bookstore.controller;

import com.bookstore.dto.BookDTO;
import com.bookstore.model.Book;
import com.bookstore.dto.BookMapper;
import com.bookstore.service.BookService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    /**
     * Retrieves all books.
     * Accessible only to authenticated users.
     *
     * @return ResponseEntity containing a list of BookDTOs and HTTP status 200.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDTO> bookDTOs = books.stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    /**
     * Retrieves a book by its ID.
     * Accessible only to authenticated users.
     *
     * @param id the ID of the book
     * @return ResponseEntity containing the BookDTO if found, or HTTP status 404 if not found.
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(b -> new ResponseEntity<>(bookMapper.toDTO(b), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Creates a new book.
     * Accessible only to users with the ADMIN role.
     *
     * @param bookDTO the BookDTO containing book information
     * @return ResponseEntity containing the created BookDTO and HTTP status 201.
     */
    @PostMapping("/saveBook")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Book createdBook = bookService.createBook(book);
        BookDTO createdBookDTO = bookMapper.toDTO(createdBook);
        return new ResponseEntity<>(createdBookDTO, HttpStatus.CREATED);
    }

    /**
     * Updates an existing book by its ID.
     * Accessible only to users with the ADMIN role.
     *
     * @param id      the ID of the book to update
     * @param bookDTO the updated BookDTO
     * @return ResponseEntity containing the updated BookDTO and HTTP status 200 if found, or 404 if not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Optional<Book> updatedBook = bookService.updateBook(id, book);
        return updatedBook.map(b -> new ResponseEntity<>(bookMapper.toDTO(b), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a book by its ID.
     * Accessible only to users with the ADMIN role.
     *
     * @param id the ID of the book to delete
     * @return ResponseEntity with HTTP status 204 if deletion is successful, or 404 if the book is not found.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBook(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Searches for books based on title, author, and genre with pagination.
     * Accessible only to authenticated users.
     *
     * @param title    the title keyword (optional)
     * @param author   the author keyword (optional)
     * @param genre    the genre keyword (optional)
     * @param pageable pagination information
     * @return ResponseEntity containing a paginated list of BookDTOs and HTTP status 200.
     */
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<BookDTO>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            Pageable pageable) {
        Page<Book> booksPage = bookService.searchBooks(title, author, genre, pageable);
        Page<BookDTO> bookDTOPage = booksPage.map(bookMapper::toDTO);
        return new ResponseEntity<>(bookDTOPage, HttpStatus.OK);
    }




//    @Autowired
//    private BookService bookService;
//
//    @Autowired
//    private final BookMapper bookMapper;
//
//    private final Logger LOGGER =
//            LoggerFactory.getLogger(BookController.class);
//
//    @GetMapping("/")
//    public String hello() {
//        return "Hello World!";
//    }
//
//
//    @PostMapping("/saveBook")
//    public BookDTO saveBook(@Valid @RequestBody BookDTO book) {
//        LOGGER.info("Inside saveBook of BookController");
//        return bookService.saveBook(book);
//    }
//
//    @GetMapping("/booklist")
//    public List<BookDTO> fetchBookList() {
//        LOGGER.info("Inside fetchBookList of BookController");
//        return bookService.fetchBookList();
//    }
//
//    @GetMapping("/{id}")
//    public BookDTO fetchBookByBookId(@PathVariable("id") Long bookId) {
//        return bookService.fetchBookById(bookId);
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteBookByBookId(@PathVariable("id") Long bookId) {
//        bookService.deleteBookById(bookId);
//        return "Book deleted Successfully";
//    }
//
//    @PutMapping("/{id}")
//    public BookDTO updateBook(@PathVariable("id") Long bookId,
//                              @RequestBody BookDTO book) {
//        return bookService.updateBook(bookId, book);
//    }
//
//    @GetMapping("/title/{title}")
//    public BookDTO fetchBookByTitle(@PathVariable("title") String bookTitle) {
//        return bookService.fetchBookByTitle(bookTitle);
//    }
//
//    @GetMapping("/author/{author}")
//    public BookDTO fetchBookByAuthor(@PathVariable("author") String bookAuthor) {
//        return bookService.fetchBookByAuthor(bookAuthor);
//    }
//
//    @GetMapping("/genre/{genre}")
//    public BookDTO fetchBookByGenre(@PathVariable("genre") String bookGenre) {
//        return bookService.fetchBookByGenre(bookGenre);
//    }
//
//    @GetMapping("/welcome")
//    public String welcome() {
//        return "Welcome to the unprotected page!";
//    }
}
