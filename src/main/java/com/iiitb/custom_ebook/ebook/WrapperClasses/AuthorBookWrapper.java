package com.iiitb.custom_ebook.ebook.WrapperClasses;

import com.iiitb.custom_ebook.ebook.Author.Author;
import com.iiitb.custom_ebook.ebook.Book.Book;

public class AuthorBookWrapper {


    private Book book_name;
    private Author author_name;
    private String ISBN;
    private String publisher;

    public AuthorBookWrapper() {
    }

    public AuthorBookWrapper(Book book_name, Author author_name) {
        this.book_name = book_name;
        this.author_name = author_name;
    }

    public Book getBook_name() {
        return book_name;
    }

    public void setBook_name(Book book_name) {
        this.book_name = book_name;
    }

    public Author getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(Author author_name) {
        this.author_name = author_name;
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
