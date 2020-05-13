package com.iiitb.custom_ebook.ebook.Exceptions;

public class EmptyException extends RuntimeException{
    public EmptyException() {
        super();
    }
    public EmptyException(String message)
    {
        super(message);
    }
}
