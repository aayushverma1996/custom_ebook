package com.iiitb.custom_ebook.ebook.Book;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iiitb.custom_ebook.ebook.Author.Author;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NaturalId
    @Column(name = "isbn",length = 20)
    private String ISBN;

    @Column(name="publisher",length = 100)
    private String publisher;

    @Column(length=50,unique = true,nullable = false)
    private String book_name;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="author_id")
    private Author author;

    @JsonIgnore
    @OneToMany(mappedBy = "book",fetch = FetchType.LAZY)
    private List<BookComponents> bookComponentsList;

    public Book() {
    }

    public Book(String book_name)
    {
        this.book_name=book_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
