package com.example.Controller;

import com.example.Bean.Book;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class BookController {
    @RequestMapping(value = {"/book","/getbook"})
    public Book book(){
        Book book =  new Book();
        book.setAuthor("罗贯中");
        book.setPrice(30f);
        book.setPublicationDate(new Date());
        book.setName("三国演义");
        return book;
    }
}
