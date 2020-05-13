package com.iiitb.custom_ebook.ebook.Publisher;

import com.iiitb.custom_ebook.ebook.Author.AuthorService;
import com.iiitb.custom_ebook.ebook.Book.Book;
import com.iiitb.custom_ebook.ebook.Exceptions.EmptyException;
import com.iiitb.custom_ebook.ebook.Exceptions.FoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/publisher")
    public int createPublisher(@RequestBody Publisher publisher)
    {
        Publisher getPublisher=publisherService.fetchPublisherUsername(publisher.getUsername());
        if(getPublisher!=null)
        {
            throw new FoundException("Username already taken!! try different");
        }
        return publisherService.createNewPublisher(publisher);
    }
    @GetMapping("/publisher/{publisherName}")
    public int getPublisher(@PathVariable String publisherName )
    {
        return publisherService.getPublisherByname(publisherName).getId();
    }

    @GetMapping("/publisher/book/{pid}")
    public List<Book> getBooks(@PathVariable("pid") int pid)
    {

        Publisher temp=publisherService.getBooks(pid);
        if(temp==null)
        {
            throw new EmptyException();
        }

        return temp.getBook_list();
    }
}
