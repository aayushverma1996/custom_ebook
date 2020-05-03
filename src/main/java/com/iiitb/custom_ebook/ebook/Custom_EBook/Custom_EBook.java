package com.iiitb.custom_ebook.ebook.Custom_EBook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iiitb.custom_ebook.ebook.User.User;

import javax.persistence.*;

@Entity
@Table(name="ebooks")
public class Custom_EBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

   @Column(name="ebookname",length=100,nullable = false)
    private String eBookName;

    @Column(nullable = false)
    private String location;

    @Column(length=1,nullable = false)
    private byte status;        //1:-generated, 2:-added to cart, 3:-purchased, 4:-deleted

    @Column
    private double price;

    @Column(length = 50)
    private String generatedBy;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Custom_EBook() {
    }

    public Custom_EBook(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String geteBookName() {
        return eBookName;
    }

    public void seteBookName(String eBookName) {
        this.eBookName = eBookName;
    }
}
