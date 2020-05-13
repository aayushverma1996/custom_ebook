package com.iiitb.custom_ebook.ebook.User;

import com.iiitb.custom_ebook.ebook.Custom_EBook.Custom_EBook;
import com.iiitb.custom_ebook.ebook.Exceptions.FoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public int createUser(@RequestBody User user)
    {
        User existingUser=userService.fetchUserbyUsername(user.getUsername());

        if(existingUser!=null)
        {
            throw new FoundException("username already taken!!Try different one");
        }
        return userService.insertUser(user);
    }

    @GetMapping("/user/ebooks/{uid}")
    public List<Custom_EBook> getEBooks(@PathVariable("uid") int uid)
    {
        User user=userService.getUserbyId(uid);
        return userService.getAssociatedEBooks(user).getCustom_eBooks();
    }
}
