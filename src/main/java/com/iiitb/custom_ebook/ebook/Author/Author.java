package com.iiitb.custom_ebook.ebook.Author;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iiitb.custom_ebook.ebook.Book.Book;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="author")

public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false,length = 100,unique = true)
    private String author_name;

    @JsonIgnore
    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
    private List<Book>books;

    public Author() {
    }

    public Author(String author_name)
    {
        this.author_name=author_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        author_name = author_name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }


}
