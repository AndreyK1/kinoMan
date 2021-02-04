package ru.chuhan.demo.controllers;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.chuhan.demo.service.BookService;

import java.io.IOException;

@RestController
@RequestMapping("/book")
public class BooksController {

    @Autowired
    BookService bookService;

    @RequestMapping(path = "/parse", method = RequestMethod.GET)
    public void parse() {
        bookService.parseBook("c:\\bo\\ff.docx");
    }
}
