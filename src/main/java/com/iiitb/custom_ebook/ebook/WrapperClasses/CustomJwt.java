package com.iiitb.custom_ebook.ebook.WrapperClasses;

import com.iiitb.custom_ebook.ebook.JwtUtil.JwtUtil;

import java.util.List;

public class CustomJwt {

    private String jwtUtil;
    private String username;
    private List<String> roles;

    public CustomJwt(String jwtUtil, String username, List<String> roles) {
        this.jwtUtil = jwtUtil;
        this.username = username;
        this.roles = roles;
    }

    public String getJwtUtil() {
        return jwtUtil;
    }

    public void setJwtUtil(String jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
