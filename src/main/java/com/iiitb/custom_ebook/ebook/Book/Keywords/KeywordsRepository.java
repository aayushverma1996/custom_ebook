package com.iiitb.custom_ebook.ebook.Book.Keywords;

import com.iiitb.custom_ebook.ebook.Book.Keywords.Keywords;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface KeywordsRepository extends CrudRepository<Keywords, Integer> {

    public Optional<Keywords> findByKeyword(String keyword);

}
