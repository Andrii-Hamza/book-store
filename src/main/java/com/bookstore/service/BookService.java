package com.bookstore.service;

import com.bookstore.entity.Book;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {


    public Book saveBook(Book book);

    public List<Book> fetchBookList();

    public Book fetchBookByBookId(Long id);

    public void deleteBookByBookId(Long id);

    public Book updateBook(Long id, Book book);

    public Book fetchBookByBookTitle(String title);

    public Book fetchBookByBookAuthor(String author);

    public Book fetchBookByBookGenre(String genre);

}
