package com.iiitb.custom_ebook.ebook.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author addNewAuthor(Author author)
    {

        Author response= authorRepository.save(author);
        return response;

    }


    public List<Author> saveAuthor(List<String> authors)
    {

//         authors.parallelStream().map(Author::new).forEach(x->authorRepository.save(x));
//         List<Author> author_list=authors.parallelStream().map(x->authorRepository.findByAuthorName(x).get()).collect(Collectors.toList());
           List<Author> temp=authors.parallelStream().map(x->x.toLowerCase()).map(Author::new).collect(Collectors.toList());
           List<Author> response=new ArrayList<Author>();
           authorRepository.saveAll(temp).forEach(x->response.add(x));
           return response;
    }
}
