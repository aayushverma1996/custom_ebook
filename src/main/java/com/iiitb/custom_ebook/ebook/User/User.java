package com.iiitb.custom_ebook.ebook.User;

import com.iiitb.custom_ebook.ebook.Custom_EBook.Custom_EBook;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length=100,nullable = false)
    private String name;

    @Column(length=100)
    private String address;

    @Column(length=50,nullable = false,unique = true)
    private String user_name;

    @Column(length=50,nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Custom_EBook> custom_eBooks;

    public User() {
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Custom_EBook> getCustom_eBooks() {
        return custom_eBooks;
    }

    public void setCustom_eBooks(List<Custom_EBook> custom_eBooks) {
        this.custom_eBooks = custom_eBooks;
    }
}
