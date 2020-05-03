package com.iiitb.custom_ebook.ebook.Author;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iiitb.custom_ebook.ebook.Book.Book;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="author")

public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false,length = 100)
    private String authorName;


    public Author() {
    }


    public Author(String authorName)
    {
        this.authorName=authorName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor_name() {
        return authorName;
    }

    public void setAuthorName(String author_name) {
        author_name = author_name;
    }

}
