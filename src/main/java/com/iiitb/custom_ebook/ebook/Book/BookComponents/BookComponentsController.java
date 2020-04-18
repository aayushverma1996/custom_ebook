package com.iiitb.custom_ebook.ebook.Book.BookComponents;


import com.iiitb.custom_ebook.ebook.Book.Keywords.KeywordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
public class BookComponentsController {

    @Autowired
    private BookComponentsService bookComponentsService;

    @Autowired
    private KeywordsService keywordsService;

    @GetMapping("/bookcomponent")
    public List<BookComponents> getAllBookComponents()
    {
        return bookComponentsService.getAllBookComponents();
    }

}
