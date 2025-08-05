package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * Retrieves all books.
     *
     * @return a list of all books
     */
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book
     * @return an optional containing the book if found, or empty if not
     */
    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Creates a new book.
     *
     * @param book the book to be created
     * @return the created book
     * @throws RuntimeException if a book with the same title already exists
     */
    @Override
    public Book createBook(Book book) {
        if (bookRepository.existsByTitle(book.getTitle())) {
            throw new RuntimeException("Book with title '" + book.getTitle() + "' already exists");
        }
        return bookRepository.save(book);
    }

    /**
     * Updates an existing book.
     *
     * @param id   the ID of the book to update
     * @param book the updated book details
     * @return an optional containing the updated book if found, or empty if not
     */
    @Override
    public Optional<Book> updateBook(Long id, Book book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return Optional.of(bookRepository.save(book));
        }
        return Optional.empty();
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete
     * @return true if the book was deleted, false if not found
     */
    @Override
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Searches for books by title, author, and genre.
     *
     * @param title    the title keyword (optional)
     * @param author   the author keyword (optional)
     * @param genre    the genre keyword (optional)
     * @param pageable pagination information
     * @return a paginated list of books matching the search criteria
     */
    @Override
    public Page<Book> searchBooks(String title, String author, String genre, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCase(
                title != null ? title : "",
                author != null ? author : "",
                genre != null ? genre : "",
                pageable
        );
    }





//    @Autowired
//    private BookRepository bookRepository;
//
//    @Override
//    public Book saveBook(Book book) {
//        return bookRepository.save(book);
//    }
//
//    @Override
//    public List<Book> fetchBookList() {
//        return bookRepository.findAll();
//    }
//
//    @Override
//    public Book fetchBookById(Long bookId) {
//        return bookRepository.findById(bookId).get();
//    }
//
//    @Override
//    public void deleteBookById(Long id) {
//        bookRepository.deleteById(id);
//    }
//
//    @Override
//    public Book updateBook(Long id, Book book) {
//        Book bookDB = bookRepository.findById(id).get();
//
//        if(Objects.nonNull(book.getTitle()) &&
//        !"".equalsIgnoreCase(book.getTitle())) {
//            bookDB.setTitle(book.getTitle());
//        }
//
//        if(Objects.nonNull(book.getGenre()) &&
//                !"".equalsIgnoreCase(book.getGenre())) {
//            bookDB.setGenre(book.getGenre());
//        }
//
//        if(Objects.nonNull(book.getPrice()) &&
//                !"".equalsIgnoreCase(String.valueOf(book.getPrice()))) {
//            bookDB.setPrice(book.getPrice());
//        }
//
//        if(Objects.nonNull(book.getAuthor()) &&
//                !"".equalsIgnoreCase(book.getAuthor())) {
//            bookDB.setAuthor(book.getAuthor());
//        }
//        return bookRepository.save(bookDB);
//    }
//
//    @Override
//    public BookDTO fetchBookByTitle(String title) {
//        return bookRepository.findByTitleIgnoreCase(title);
//    }
//
//    @Override
//    public BookDTO fetchBookByAuthor(String author) {
//        return bookRepository.findByAuthorIgnoreCase(author);
//    }
//
//    @Override
//    public BookDTO fetchBookByGenre(String genre) {
//        return bookRepository.findByGenreIgnoreCase(genre);
//    }

}
