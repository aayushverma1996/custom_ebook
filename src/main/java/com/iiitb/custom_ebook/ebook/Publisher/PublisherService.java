package com.iiitb.custom_ebook.ebook.Publisher;

import com.iiitb.custom_ebook.ebook.Book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher getPublisher(int id)
    {
        return publisherRepository.findById(id).get();
    }

    public int createNewPublisher(Publisher publisher)
    {

        return publisherRepository.save(publisher).getId();
    }

    public Publisher getPublisherByname(String pname)
    {
        return publisherRepository.findByName(pname).get();
    }

    public Publisher getBooks(int pid)
    {
        return publisherRepository.getBooks(pid).orElse(null);
    }

    public Publisher fetchPublisherUsername(String username)
    {
        return publisherRepository.findByUsername(username).orElse(null);
    }
}
