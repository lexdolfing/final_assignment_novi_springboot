package com.novi.DemoDrop.Dto.InputDto;

public class AuthenticationRequest {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}