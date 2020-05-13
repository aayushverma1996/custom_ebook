package com.iiitb.custom_ebook.ebook.Publisher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PublisherRepository extends CrudRepository<Publisher,Integer> {

    public Optional<Publisher> findByName(String pname);

    @Query("select p from Publisher p join fetch p.book_list where p.id=:pid")
    public Optional<Publisher> getBooks(@Param("pid") int pid);

    public Optional<Publisher> findByUsername(String username);

}
