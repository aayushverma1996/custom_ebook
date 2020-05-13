package com.iiitb.custom_ebook.ebook.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {

    @Query("select u from User u join fetch u.custom_eBooks where u.id=:id")
     Optional<User> getebooks(@Param("id") int id);

    Optional<User> findByUsername(String username);
}
