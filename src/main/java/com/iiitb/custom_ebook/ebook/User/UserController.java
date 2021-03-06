package com.iiitb.custom_ebook.ebook.User;

import com.iiitb.custom_ebook.ebook.Custom_EBook.Custom_EBook;
import com.iiitb.custom_ebook.ebook.Exceptions.EmptyException;
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
        System.out.println(user.getPassword());
        if(existingUser!=null)
        {
            throw new FoundException("username already taken!!Try different one");
        }
        return userService.insertUser(user).getId();
    }

    @GetMapping("/user/ebooks/{uid}")
    public List<Custom_EBook> getEBooks(@PathVariable("uid") int uid)
    {
        User user=userService.getAssociatedEBooks(uid);
        if(user==null)
        {
            throw new EmptyException();
        }
        List<Custom_EBook>temp=user.getCustom_eBooks();
        return temp;
    }
}
