package com.iiitb.custom_ebook.ebook.Book;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iiitb.custom_ebook.ebook.Author.Author;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Publisher.Publisher;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NaturalId
    @Column(name = "isbn",length = 20,nullable = false)
    private String ISBN;

    @Column(length=50,nullable = false)
    private String bookName;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "author_book",joinColumns = {@JoinColumn(name="author_id")},inverseJoinColumns = {@JoinColumn(name="book_id")})
    private List<Author> author_list;

    @JsonIgnore
    @OneToMany(mappedBy = "book",fetch = FetchType.LAZY)
    private List<BookComponents> bookComponentsList;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="publisher_id")
    private Publisher publisher;

    public Book() {
    }

    public Book(String book_name)
    {
        this.bookName=book_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook_name() {
        return bookName;
    }

    public void setBook_name(String book_name) {
        this.bookName = book_name;
    }

    public List<BookComponents> getBookComponentsList() {
        return bookComponentsList;
    }

    public void setBookComponentsList(List<BookComponents> bookComponentsList) {
        this.bookComponentsList = bookComponentsList;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public List<Author> getAuthor_list() {
        return author_list;
    }

    public void setAuthor_list(List<Author> author_list) {
        this.author_list = author_list;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
