package com.yuboz.springbootapi.service;

import com.yuboz.springbootapi.domain.Book;
import com.yuboz.springbootapi.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookrepository;

    @Override
    public List<Book> findAllBooks() {
        return bookrepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookrepository.findById(id).orElse(null);
    }

    @Override
    public Book saveBook(Book book) {
        return bookrepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookrepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookrepository.deleteById(id);
    }

    @Override
    public void deleteAllBook() {
        bookrepository.deleteAll();

    }
}
