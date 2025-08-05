package com.bookstore.service;

import com.bookstore.dto.BookDTO;
import com.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    Book createBook(Book book);

    Optional<Book> updateBook(Long id, Book book);

    boolean deleteBook(Long id);

    Page<Book> searchBooks(String title, String author, String genre, Pageable pageable);



//    public Book saveBook(Book book);
//
//    public List<Book> fetchBookList();
//
//    public Book fetchBookById(Long id);
//
//    public void deleteBookById(Long id);
//
//    public Book updateBook(Long id, Book book);
//
//    public Book fetchBookByTitle(String title);
//
//    public Book fetchBookByAuthor(String author);
//
//    public Book fetchBookByGenre(String genre);

}
