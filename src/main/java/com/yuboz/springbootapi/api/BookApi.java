package com.yuboz.springbootapi.api;

import com.yuboz.springbootapi.domain.Book;
import com.yuboz.springbootapi.dto.BookDTO;
import com.yuboz.springbootapi.service.BookService;
import com.yuboz.springbootapi.util.CustomBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookApi {

    @Autowired
    private BookService bookService;

    /**
     * List all books
     * @return
     */

    @GetMapping("/books")
    public ResponseEntity<?> listAllBooks(){
        List<Book> books = bookService.findAllBooks();
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    /**
     * List a book by id
     * @return
     */

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id){
        Book book = bookService.getBookById(id);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    /**
     * Add a book to the list
     * @return
     */
    @PostMapping("/books")
    public ResponseEntity<?> saveBook(@RequestBody Book book){
        Book savedbook = bookService.saveBook(book);
        return new ResponseEntity<Object>(savedbook, HttpStatus.CREATED);
    }

    /**
     * Update a book in the list
     * @return
     */
    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,@RequestBody BookDTO bookDTO){
        Book oldbook = bookService.getBookById(id);
        bookDTO.convertToBook(oldbook);
        Book updatedbook = bookService.saveBook(oldbook);
        return new ResponseEntity<Object>(updatedbook, HttpStatus.OK);

    }





    /**
     * Delete a book in the list
     * @return
     */
    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        bookService.deleteBookById(id);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete all books in the list
     * @return
     */
    @DeleteMapping("/books")
    public ResponseEntity<?> deleteAllBook(){
        bookService.deleteAllBook();
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }



}
