package com.iiitb.custom_ebook.ebook.Book.Keywords;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="keywords")
public class Keywords {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false,unique = true)
    private String keyword;

    @ManyToMany(mappedBy = "keywordsList")
    private List<BookComponents> bookComponentsList;

    public Keywords() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<BookComponents> getBookComponentsList() {
        return bookComponentsList;
    }

    public void setBookComponentsList(List<BookComponents> bookComponentsList) {
        this.bookComponentsList = bookComponentsList;
    }

    @Override
    public String toString() {
        return "Keywords{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", bookComponentsList=" + bookComponentsList +
                '}';
    }
}
