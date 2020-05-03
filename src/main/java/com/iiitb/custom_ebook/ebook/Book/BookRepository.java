package com.iiitb.custom_ebook.ebook.Book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Integer> {

    @Query("select b from Book b join fetch b.bookComponentsList where b.id=:id")
    public Book getComponents(@Param("id") int id);


}
