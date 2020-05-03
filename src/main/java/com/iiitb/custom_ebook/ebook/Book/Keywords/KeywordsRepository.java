package com.iiitb.custom_ebook.ebook.Book.Keywords;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Iterator;
import java.util.Optional;


public interface KeywordsRepository extends CrudRepository<Keywords, Integer> {

    @Query("select k from Keywords k join fetch k.bookComponentsList where k.keyword=:keyword")
    public Optional<Keywords> getComponents(@Param("keyword") String keyword);

    public Optional<Keywords> findByKeyword(String keyword);

}
