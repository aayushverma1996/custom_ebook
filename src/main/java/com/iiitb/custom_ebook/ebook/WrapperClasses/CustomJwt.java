package com.iiitb.custom_ebook.ebook.WrapperClasses;

import com.iiitb.custom_ebook.ebook.JwtUtil.JwtUtil;

import java.util.List;

public class CustomJwt {

    private String jwtUtil;
    private String username;
    private List<String> roles;
    private int id;
    private String name;

    public CustomJwt(String jwtUtil, String username, List<String> roles,int id,String name) {
        this.jwtUtil = jwtUtil;
        this.username = username;
        this.roles = roles;
        this.name=name;
        this.id=id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
