package com.iiitb.custom_ebook.ebook.WrapperClasses;

import com.iiitb.custom_ebook.ebook.Author.Author;
import com.iiitb.custom_ebook.ebook.Book.Book;

public class AuthorBookWrapper {


    private Book book_name;
    private String authors;
    private String isbn;
    private int publisher_id;

    public AuthorBookWrapper() {
    }

    public AuthorBookWrapper(Book book_name) {
        this.book_name = book_name;

    }

    public Book getBook_name() {
        return book_name;
    }

    public void setBook_name(Book book_name) {
        this.book_name = book_name;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublisher() {
        return publisher_id;
    }

    public void setPublisher(int publisher) {
        this.publisher_id = publisher;
    }
}
