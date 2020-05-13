package com.iiitb.custom_ebook.ebook.JwtUtil;

public class JwtResponse {
    private String jwtString;

    public JwtResponse(String jwtString){
        this.jwtString = jwtString;
    }

    public String getJwtString() {
        return jwtString;
    }


}
