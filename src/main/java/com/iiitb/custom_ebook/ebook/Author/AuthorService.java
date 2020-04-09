package com.iiitb.custom_ebook.ebook.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author addNewAuthor(Author author)
    {

        Author response= authorRepository.save(author);
        return response;

    }
}
