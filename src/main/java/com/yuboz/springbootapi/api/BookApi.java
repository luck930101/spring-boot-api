package com.yuboz.springbootapi.api;

import com.yuboz.springbootapi.domain.Book;
import com.yuboz.springbootapi.dto.BookDTO;
import com.yuboz.springbootapi.exception.InvalidRequestException;
import com.yuboz.springbootapi.exception.NotFoundException;
import com.yuboz.springbootapi.service.BookService;
import com.yuboz.springbootapi.util.CustomBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        if(books.isEmpty()){
            throw new NotFoundException("Books not found");
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    /**
     * List a book by id
     * @return
     */

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id){
        Book book = bookService.getBookById(id);

        if(book == null){
            throw new NotFoundException(String.format("Book with id %s is not found",id));
        }
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    /**
     * Add a book to the list
     * @return
     */
    @PostMapping("/books")
    public ResponseEntity<?> saveBook(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new InvalidRequestException("Invalid parameter",bindingResult);
        }


        Book savedbook = bookService.saveBook(bookDTO.convertToBook());
        return new ResponseEntity<Object>(savedbook, HttpStatus.CREATED);
    }

    /**
     * Update a book in the list
     * @return
     */
    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO,BindingResult bindingResult ){
        Book oldbook = bookService.getBookById(id);
        if(oldbook == null){
            throw new NotFoundException(String.format("Book with id %s is not found",id));
        }
        if(bindingResult.hasErrors()){
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        bookDTO.convertToBook(oldbook);
        Book updatedbook = bookService.saveBook(oldbook);
        return new ResponseEntity<Object>(updatedbook, HttpStatus.OK);

    }
//    @PutMapping("/books/{id}")
//    public void  updateBook(@PathVariable Long id ){
//        System.out.println("checkpoint1");
//        System.out.println(id);
//
//    }





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
