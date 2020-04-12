package com.iiitb.custom_ebook.ebook.Book.BookComponents;

import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class BookComponentsService {

    @Autowired
    private BookComponentsRepository bookComponentsRepository;

    public BookComponents insertNewEntry(BookComponents bookComponents)
    {
        return bookComponentsRepository.save(bookComponents);
    }

    public List<BookComponents> getAllBookComponents()
    {
        Iterable<BookComponents> r=bookComponentsRepository.findAll();
        List<BookComponents> response=new ArrayList<BookComponents>();
        for(BookComponents b:r)
        {
            response.add(b);
        }
        return  response;
    }
}
