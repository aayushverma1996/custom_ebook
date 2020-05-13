package com.iiitb.custom_ebook.ebook.SecurityConfiguration;


import com.iiitb.custom_ebook.ebook.JwtUtil.JwtUtil;
import com.iiitb.custom_ebook.ebook.Publisher.MyPublisherDetailsService;
import com.iiitb.custom_ebook.ebook.Publisher.Publisher;
import com.iiitb.custom_ebook.ebook.Publisher.PublisherService;
import com.iiitb.custom_ebook.ebook.User.User;
import com.iiitb.custom_ebook.ebook.User.UserService;
import com.iiitb.custom_ebook.ebook.WrapperClasses.CustomJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin("*")
@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MyUserDetailsService userDetailService;
    @Autowired
    MyPublisherDetailsService publisherDetailsService;
    @Autowired
    UserService userService;

    @Autowired
    PublisherService publisherService;
    @Autowired
    JwtUtil jwtTokenUtil;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<CustomJwt> createAuthenticationToken(@RequestParam("username") String username,
                                                       @RequestParam("password") String password
    ) throws Exception{
        UserDetails userDetails=null;
        String jwt = null;
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,
                            password,null)

            );
            userDetails = userDetailService.loadUserByUsername(username);
             User temp=userService.fetchUserbyUsername(username);
            jwt = jwtTokenUtil.generateToken(userDetails);
            List<String> roles=userDetails.getAuthorities().stream().map(x->x.toString()).collect(Collectors.toList());
            CustomJwt customJwt=new CustomJwt(jwt,userDetails.getUsername(),roles ,temp.getId(),temp.getName()  );
            return ResponseEntity.ok(customJwt);

        }catch (Exception e){
            System.out.println("User Not Authenticated");
            throw new UsernameNotFoundException("Username or password is incorrect");
        }



    }


    @RequestMapping(value = "/publisherlogin",method = RequestMethod.POST)
    public ResponseEntity<CustomJwt> createAuthenticationTokenPublisher(@RequestParam("username") String username,
                                                                        @RequestParam("password") String password
    ) throws Exception{
        UserDetails userDetails=null;
        String jwt = null;
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,
                            password,null)

            );
            userDetails = publisherDetailsService.loadUserByUsername(username);
            Publisher temp=publisherService.fetchPublisherUsername(username);
            jwt = jwtTokenUtil.generateToken(userDetails);
            List<String> roles=userDetails.getAuthorities().stream().map(x->x.toString()).collect(Collectors.toList());
            CustomJwt customJwt=new CustomJwt(jwt,userDetails.getUsername(),roles,temp.getId(),temp.getName()   );
            return ResponseEntity.ok(customJwt);

        }catch (Exception e){
            System.out.println("User Not Authenticated");
            throw new UsernameNotFoundException("Username or password is incorrect");
        }

    }


}