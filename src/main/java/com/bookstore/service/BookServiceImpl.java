package com.bookstore.service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> fetchBookList() {
        return bookRepository.findAll();
    }

    @Override
    public Book fetchBookByBookId(Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    @Override
    public void deleteBookByBookId(Long bookId) {
        bookRepository.deleteById(bookId);
    }

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

    @Override
    public Book fetchBookByBookTitle(String bookTitle) {
        return bookRepository.findByBookTitleIgnoreCase(bookTitle);
    }

    @Override
    public Book fetchBookByBookAuthor(String bookAuthor) {
        return bookRepository.findByBookAuthorIgnoreCase(bookAuthor);
    }

    @Override
    public Book fetchBookByBookGenre(String bookGenre) {
        return bookRepository.findByBookGenreIgnoreCase(bookGenre);
    }

}
