package com.iiitb.custom_ebook.ebook.Publisher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iiitb.custom_ebook.ebook.Book.Book;

import javax.persistence.*;
import java.util.List;

@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(length = 100,nullable = false)
    private String name;

    @Column(length=100)
    private String address;

    @Column(length=50,nullable = false,unique = true)
    private String username;
    @JsonIgnore
    @Column(length=50,nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "publisher")
    private List<Book> book_list;

    @Column
    private boolean active;

    @Column
    private  String roles;

    public Publisher() {
    }
    public Publisher(String name,String user_name,String password)
    {
        this.name=name;
        this.username=user_name;
        this.password=password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user_name) {
        this.username = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getBook_list() {
        return book_list;
    }

    public void setBook_list(List<Book> book_list) {
        this.book_list = book_list;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
