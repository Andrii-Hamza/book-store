package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the {@link BookService} interface for managing books.
 */
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Retrieves all books.
     *
     * @return a list of all books
     */
    @Override
    public List<Book> fetchBookList() {
        return bookRepository.findAll();
    }

    /**
     * Creates a new book.
     *
     * @param book the book to be created
     * @return the created book
     */
    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param bookId the ID of the book
     * @return an optional containing the book if found, or empty if not
     */
    @Override
    public Book fetchBookByBookId(Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    /**
     * Deletes a book by its ID.
     *
     * @param bookId the ID of the book to delete
     */
    @Override
    public void deleteBookByBookId(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    /**
     * Updates an existing book.
     *
     * @param bookId the ID of the book to update
     * @param book the updated book details
     * @return an optional containing the updated book if found, or empty if not
     */
    @Override
    public Book updateBook(Long bookId, Book book) {
        Book bookDB = bookRepository.findById(bookId).get();

        if(Objects.nonNull(book.getBookTitle()) &&
        !"".equalsIgnoreCase(book.getBookTitle())) {
            bookDB.setBookTitle(book.getBookTitle());
        }

        if(Objects.nonNull(book.getBookGenre()) &&
                !"".equalsIgnoreCase(book.getBookGenre())) {
            bookDB.setBookGenre(book.getBookGenre());
        }

        if(Objects.nonNull(book.getBookPrice()) &&
                !"".equalsIgnoreCase(String.valueOf(book.getBookPrice()))) {
            bookDB.setBookPrice(book.getBookPrice());
        }

        if(Objects.nonNull(book.getBookAuthor()) &&
                !"".equalsIgnoreCase(book.getBookAuthor())) {
            bookDB.setBookAuthor(book.getBookAuthor());
        }
        return bookRepository.save(bookDB);
    }

    /**
     * Searches for books by title.
     *
     * @param title    the title keyword (optional)
     * @return a paginated list of books matching the search criteria
     */
    @Override
    public Book fetchBookByBookTitle(String title) {
        return bookRepository.findByBookTitleIgnoreCase(title);
    }

    /**
     * Searches for books by author.
     *
     * @param author the author keyword (optional)
     * @return a paginated list of books matching the search criteria
     */
    @Override
    public Book fetchBookByBookAuthor(String author) {
        return bookRepository.findByBookAuthorIgnoreCase(author);
    }

    /**
     * Searches for books by genre.
     *
     * @param genre the genre keyword (optional)
     * @return a paginated list of books matching the search criteria
     */
    @Override
    public Book fetchBookByBookGenre(String genre) {
        return bookRepository.findByBookGenreIgnoreCase(genre);
    }
}
