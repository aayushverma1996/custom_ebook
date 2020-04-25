package com.iiitb.custom_ebook.ebook.Book.Keywords;

import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin(origins = "*")
@RestController
public class KeywordsController {


    @Autowired
    private KeywordsService keywordsService;


    @GetMapping("/keywords/{keyword}")
    public Keywords getSpecificKeyword(@PathVariable String keyword)
    {
        return keywordsService.getSpecificKeyword(keyword);
    }

    @GetMapping("/keywords")
    public List<Keywords> getAllKeywords()
    {
        return keywordsService.getAllKeywords();
    }


    @GetMapping("/keywords/bookComp/{keyword}")
    public List<BookComponents> get(@PathVariable String keyword)
    {
        Keywords k = keywordsService.getSpecificKeyword(keyword);
        return k.getBookComponentsList();

    }

}
