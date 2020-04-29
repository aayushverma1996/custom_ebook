package com.iiitb.custom_ebook.ebook.Book;

import com.iiitb.custom_ebook.ebook.Author.Author;
import com.iiitb.custom_ebook.ebook.Author.AuthorService;
import com.iiitb.custom_ebook.ebook.WrapperClasses.AuthorBookWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
   private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;

    public Book addNewBook(AuthorBookWrapper authorBookWrapper)
    {
        //here we need to add method to add new author extracted from wrapper

         Author temp_author=authorService.addNewAuthor(authorBookWrapper.getAuthor_name());
         authorBookWrapper.getBook_name().setAuthor(temp_author);
         authorBookWrapper.getBook_name().setISBN(authorBookWrapper.getISBN());
         authorBookWrapper.getBook_name().setPublisher(authorBookWrapper.getPublisher());
         return  bookRepository.save(authorBookWrapper.getBook_name());

    }

    public Book getBookbyId(int id){

        return bookRepository.findById(id).orElse(null);
    }

}
