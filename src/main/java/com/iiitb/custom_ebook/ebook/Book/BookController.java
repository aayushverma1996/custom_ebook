package com.iiitb.custom_ebook.ebook.Book;

import com.iiitb.custom_ebook.ebook.Author.Author;
import com.iiitb.custom_ebook.ebook.WrapperClasses.AuthorBookWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;


    @PostMapping("/bookauthor")
    public int createBookAuthor(@RequestBody AuthorBookWrapper authorBookWrapper)
    {

        Book book= bookService.addNewBook(authorBookWrapper);
        return book.getId();
    }


}
