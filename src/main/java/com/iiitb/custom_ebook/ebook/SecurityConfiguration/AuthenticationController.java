package com.iiitb.custom_ebook.ebook.SecurityConfiguration;


import com.iiitb.custom_ebook.ebook.JwtUtil.JwtResponse;
import com.iiitb.custom_ebook.ebook.JwtUtil.JwtUtil;
import com.iiitb.custom_ebook.ebook.WrapperClasses.CustomJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MyUserDetailsService userDetailService;
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
            System.out.println(userDetails.getUsername());
            jwt = jwtTokenUtil.generateToken(userDetails);

        }catch (Exception e){
            System.out.println("User Not Authenticated");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<String> roles=userDetails.getAuthorities().stream().map(x->x.toString()).collect(Collectors.toList());
        CustomJwt customJwt=new CustomJwt(jwt,userDetails.getUsername(),roles);
        return ResponseEntity.ok(customJwt);
    }
}