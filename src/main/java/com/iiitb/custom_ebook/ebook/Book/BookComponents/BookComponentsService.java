package com.iiitb.custom_ebook.ebook.Book.BookComponents;

import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookComponentsService {

    @Autowired
    private BookComponentsRepository bookComponentsRepository;

    public BookComponents insertNewEntry(BookComponents bookComponents)
    {
        return bookComponentsRepository.save(bookComponents);
    }
}
