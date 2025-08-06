package com.bookstore.service;

import com.bookstore.entity.Book;
import java.util.List;

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
