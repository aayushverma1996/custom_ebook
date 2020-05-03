package com.iiitb.custom_ebook.ebook.User;

import com.iiitb.custom_ebook.ebook.Custom_EBook.Custom_EBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public int createUser(@RequestBody User user)
    {
        return userService.insertUser(user);
    }

    @GetMapping("/user/ebooks")
    public List<Custom_EBook> getEBooks(@RequestParam("uid") int uid)
    {
        User user=userService.getUserbyId(uid);
        return userService.getAssociatedEBooks(user).getCustom_eBooks();
    }
}
