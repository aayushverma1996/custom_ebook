package com.iiitb.custom_ebook.ebook.Book;

import com.iiitb.custom_ebook.ebook.Author.Author;
import com.iiitb.custom_ebook.ebook.Author.AuthorService;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Publisher.Publisher;
import com.iiitb.custom_ebook.ebook.Publisher.PublisherService;
import com.iiitb.custom_ebook.ebook.WrapperClasses.AuthorBookWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    @Autowired
   private BookRepository bookRepository;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private PublisherService publisherService;

    public Book addNewBook(AuthorBookWrapper authorBookWrapper)
    {
        //here we need to add method to add new author extracted from wrapper

         String[] authors=authorBookWrapper.getAuthors().split(",");
         List<Author> authorList=authorService.saveAuthor(Arrays.asList(authors));

         //authorBookWrapper.getBook_name().setAuthor(temp_author);
         authorBookWrapper.getBook_name().setAuthor_list(authorList);
         authorBookWrapper.getBook_name().setISBN(authorBookWrapper.getIsbn().toLowerCase());
         Publisher publisher=publisherService.getPublisher(authorBookWrapper.getPublisher_id());
         authorBookWrapper.getBook_name().setPublisher(publisher);
         return  bookRepository.save(authorBookWrapper.getBook_name());

    }

    public Book getBookbyId(int id){

        return bookRepository.findById(id).orElse(null);
    }

    public List<BookComponents> getBookComponentList(int id)
    {
        Book book=bookRepository.findById(id).get();
        return bookRepository.getComponents(book.getId()).getBookComponentsList();
    }

}
