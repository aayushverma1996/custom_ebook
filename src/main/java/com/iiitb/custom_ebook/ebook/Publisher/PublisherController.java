package com.iiitb.custom_ebook.ebook.Publisher;

import com.iiitb.custom_ebook.ebook.Author.AuthorService;
import com.iiitb.custom_ebook.ebook.Book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("/publisher")
    public int createPublisher(@RequestBody Publisher publisher)
    {
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
        return publisherService.getBooks(pid).getBook_list();
    }
}
