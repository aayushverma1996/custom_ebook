package com.iiitb.custom_ebook.ebook.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CentralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFoundError(NotFoundException e)
    {
        CustomError err=new CustomError(HttpStatus.NOT_FOUND,e.getMessage());
        HttpHeaders h=new HttpHeaders();
        h.set("Error-status","NOT FOUND");
        return new ResponseEntity<CustomError>(err,h,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(FoundException.class)
    public ResponseEntity<CustomError> resourceFoundError(FoundException e)
    {
        CustomError err=new CustomError(HttpStatus.FOUND,e.getMessage());
        HttpHeaders h=new HttpHeaders();
        h.set("Error-status","AlREADY FOUND");
        return new ResponseEntity<CustomError>(err,h,HttpStatus.FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomError> usernameNotMatched(UsernameNotFoundException e)
    {
        CustomError err=new CustomError(HttpStatus.NOT_FOUND,e.getMessage());
        HttpHeaders h=new HttpHeaders();
        h.set("Error-status","NOT FOUND");
        return new ResponseEntity<CustomError>(err,h,HttpStatus.NOT_FOUND);
    }

}
