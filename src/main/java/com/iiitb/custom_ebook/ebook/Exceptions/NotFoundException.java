package com.iiitb.custom_ebook.ebook.Exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException()
    {
        super();
    }
    public NotFoundException(String message)
    {
        super(message);
    }
}
