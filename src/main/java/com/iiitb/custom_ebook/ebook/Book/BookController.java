package com.iiitb.custom_ebook.ebook.Book;

import com.iiitb.custom_ebook.ebook.Author.Author;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Exceptions.FoundException;
import com.iiitb.custom_ebook.ebook.Exceptions.NotFoundException;
import com.iiitb.custom_ebook.ebook.WrapperClasses.AuthorBookWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;


    @PostMapping("/bookauthor")
    public int createNewBook(@RequestBody AuthorBookWrapper authorBookWrapper)
    {
        Book getBook=bookService.fetchBookISBN(authorBookWrapper.getIsbn());

        if(getBook!=null)
        {
            throw new FoundException("ISBN already present.");
        }

        Book book= bookService.addNewBook(authorBookWrapper);
        return book.getId();
    }

    @GetMapping("/book/bookComponents")
    public List<BookComponents> getComponents(@RequestParam("book_id") int book_id)
    {
        return bookService.getBookComponentList(book_id);
    }
    @GetMapping("/book/{bookid}")
    public Book getbook(@PathVariable("bookid") int bookid)
    {
        Book b=bookService.getBookbyId(bookid);
        if(b==null)
        {
            throw new NotFoundException("Book of provided id is not present");
        }
        return b;
    }


}
