package com.yuboz.springbootapi.dto;

import com.yuboz.springbootapi.domain.Book;
import com.yuboz.springbootapi.util.CustomBeanUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String author;
    @NotBlank
    private String description;
    @NotNull
    private int status;

    public BookDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Book convertToBook(){
        return new BookConvert().convert(this);
    }

    public void convertToBook(Book book){
        new BookConvert().convert(this,book);
    }

    private class BookConvert implements Convert<BookDTO, Book>{

        @Override
        public Book convert(BookDTO bookDTO, Book book) {
            String[] nullPropertyNames= CustomBeanUtils.getNullPropertyName(bookDTO);
            BeanUtils.copyProperties(bookDTO,book,nullPropertyNames);
            return book;
        }

        @Override
        public Book convert(BookDTO bookDTO) {

            Book book = new Book();
            BeanUtils.copyProperties(bookDTO,book);
            return book;
        }
    }
}
