package com.bookstore.service;

import com.bookstore.entity.Book;

import java.nio.channels.FileChannel;
import java.util.List;

public interface BookService {

    public Book saveBook(Book book);

    public List<Book> fetchBookList();

    public Book fetchBookByBookId(Long bookId);

    public void deleteBookByBookId(Long bookId);

    public Book updateBook(Long bookId, Book book);

    public Book fetchBookByBookTitle(String bookTitle);

    public Book fetchBookByBookAuthor(String bookAuthor);

    public Book fetchBookByBookGenre(String bookGenre);
}
