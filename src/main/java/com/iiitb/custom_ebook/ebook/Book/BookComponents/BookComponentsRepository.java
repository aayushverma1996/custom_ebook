package com.iiitb.custom_ebook.ebook.Book.BookComponents;

import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Book.Keywords.Keywords;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public interface BookComponentsRepository extends CrudRepository<BookComponents,Integer> {

}
