package com.iiitb.custom_ebook.ebook.SecurityConfiguration;

import com.iiitb.custom_ebook.ebook.Publisher.Publisher;
import com.iiitb.custom_ebook.ebook.Publisher.PublisherRepository;
import com.iiitb.custom_ebook.ebook.User.User;
import com.iiitb.custom_ebook.ebook.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
      //  user.orElseThrow(()->new UsernameNotFoundException("Not Found: "+username));
        User u=user.orElse(null);
        if(u==null)
        {
            Optional<Publisher> publisher=publisherRepository.findByUsername(username);

            if(publisher.orElse(null)!=null)
            {
               MyUserDetails myUserDetails=new MyUserDetails();
               myUserDetails.setActive(true);
               myUserDetails.setPassword(publisher.get().getPassword());
               myUserDetails.setUsername(publisher.get().getUsername());
               List<String> list=new ArrayList<String>();

                List<GrantedAuthority> grn=Arrays.stream(publisher.get().getRoles().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
               myUserDetails.setAuthorityList(grn);
                return myUserDetails;
            }
            else
            {
                throw new UsernameNotFoundException("Username or password incorrect");
            }


        }
        return user.map(MyUserDetails::new).get();
    }
}
