package com.iiitb.custom_ebook.ebook.Book.Keywords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin(origins = "*")
@RestController
public class KeywordsController {



    // when frontend provides with list of keywords
//    @PostMapping("/keywords")
//    public String buildPdfKeywords(@RequestBody List<Keywords> keywords)
//    {
//        System.out.println(keywords.get(1));
//        return "ok";
//    }
    @Autowired
    private KeywordsService keywordsService;

    @PostMapping("/keywords")
    public List<Keywords> createKeywords(@RequestBody List<String> keywords)
    {
            return keywordsService.saveKeywords(keywords);
    }
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
    //update and delete need to be made
}
