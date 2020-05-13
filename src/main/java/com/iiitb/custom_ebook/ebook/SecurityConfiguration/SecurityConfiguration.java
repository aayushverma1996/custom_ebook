package com.iiitb.custom_ebook.ebook.SecurityConfiguration;

import com.iiitb.custom_ebook.ebook.JwtUtil.JwtRequestFilter;
import com.iiitb.custom_ebook.ebook.Publisher.MyPublisherDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    MyUserDetailsService userDetailsService;
    @Autowired
    MyPublisherDetailsService myPublisherDetailsService;
    @Autowired
    JwtRequestFilter jwtRequestFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {



        auth.userDetailsService(userDetailsService);
      //  auth.userDetailsService(myPublisherDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/bookcomponent").permitAll()
                .antMatchers("/keywords/bookComp/{keyword}").permitAll()
                .antMatchers("/keywords/{keyword}").permitAll()
                .antMatchers("/bookauthor").permitAll()
                .antMatchers("/book/bookComponents").permitAll()
                .antMatchers("/file").permitAll()
                .antMatchers("/purchase").permitAll()
                .antMatchers("/download").permitAll()
                .antMatchers("/publisher/{publisherName}").permitAll()
                .antMatchers("/publisher/{publisherName}").permitAll()
                .antMatchers("/publisher/book/{pid}").permitAll()
                .antMatchers("/upload").permitAll()
                .antMatchers("/user").permitAll()
                .antMatchers("/user/ebooks/{uid}").permitAll()
                .antMatchers("/publisher").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/publisherlogin").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws  Exception{
        return super.authenticationManagerBean();
    }

}
