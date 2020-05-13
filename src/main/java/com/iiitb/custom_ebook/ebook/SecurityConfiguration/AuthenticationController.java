package com.iiitb.custom_ebook.ebook.SecurityConfiguration;


import com.iiitb.custom_ebook.ebook.JwtUtil.JwtResponse;
import com.iiitb.custom_ebook.ebook.JwtUtil.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    MyUserDetailsService userDetailService;
    @Autowired
    JwtUtil jwtTokenUtil;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestParam("username") String username,
                                                       @RequestParam("password") String password
                                                       ) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,
                            password)
            );
        }catch (Exception e){
            System.out.println("User Not Authenticated");
        }
        final UserDetails userDetails = userDetailService.loadUserByUsername(username);
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
