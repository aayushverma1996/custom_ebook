package com.iiitb.custom_ebook.ebook.Book;
import java.util.*;
import com.iiitb.custom_ebook.ebook.Author.Author;

import javax.persistence.*;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(length=50,unique = true,nullable = false)
    private String book_name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Author author;

    @OneToMany(mappedBy = "book",fetch = FetchType.LAZY)
    private List<BookComponents> bookComponentsList;

    public Book() {
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
}
