package com.iiitb.custom_ebook.ebook.User;

import com.iiitb.custom_ebook.ebook.Custom_EBook.Custom_EBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public User getUserbyId(int id)
    {
        return userRepository.findById(id).get();
    }

    public int insertUser(User user)
    {
        return userRepository.save(user).getId();
    }

    public User getAssociatedEBooks(User u)
    {
        return userRepository.getebooks(u.getId()).get();
    }
}
