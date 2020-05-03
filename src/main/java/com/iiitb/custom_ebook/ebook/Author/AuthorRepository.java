package com.iiitb.custom_ebook.ebook.Author;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author,Integer> {

    public Optional<Author> findByAuthorName(String author_name);
}
