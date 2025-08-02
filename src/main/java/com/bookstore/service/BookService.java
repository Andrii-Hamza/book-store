package com.bookstore.service;

import com.bookstore.entity.Book;

import java.nio.channels.FileChannel;
import java.util.List;

public interface BookService {

    public Book saveBook(Book book);

    public List<Book> fetchBookList();

    public Book fetchBookById(Long bookId);

    public void deleteBookById(Long bookId);

    public Book updateBook(Long bookId, Book book);

    public Book fetchBookByTitle(String bookTitle);

}
