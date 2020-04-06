package com.iiitb.custom_ebook.ebook.Book;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="book_Components")
public class BookComponents {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String component_name;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Book book;

    @ManyToMany
    @JoinTable(name="component_keyword",joinColumns = {@JoinColumn(name="book_id")},inverseJoinColumns = {@JoinColumn(name="keyword_id")})
    private List<Keywords> keywordsList;

    public BookComponents() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponent_name() {
        return component_name;
    }

    public void setComponent_name(String component_name) {
        this.component_name = component_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Keywords> getKeywordsList() {
        return keywordsList;
    }

    public void setKeywordsList(List<Keywords> keywordsList) {
        this.keywordsList = keywordsList;
    }
}
