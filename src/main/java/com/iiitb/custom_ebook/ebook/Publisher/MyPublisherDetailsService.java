package com.iiitb.custom_ebook.ebook.Publisher;


import com.iiitb.custom_ebook.ebook.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MyPublisherDetailsService implements UserDetailsService {
    @Autowired
    PublisherRepository publisherRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Publisher> user = publisherRepository.findByUsername(username);
        user.orElseThrow(()->new UsernameNotFoundException("Not Found: "+username));

        Publisher u=user.get();
        MyPublisherDetails details=new MyPublisherDetails(u);
        return details;
    }
}
