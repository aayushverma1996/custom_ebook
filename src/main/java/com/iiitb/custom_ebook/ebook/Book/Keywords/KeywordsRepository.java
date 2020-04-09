package com.iiitb.custom_ebook.ebook.Book.Keywords;

import com.iiitb.custom_ebook.ebook.Book.Keywords.Keywords;
import org.springframework.data.repository.CrudRepository;

public interface KeywordsRepository extends CrudRepository<Keywords, Integer> {

    public Keywords findByKeyword(String keyword);

}
