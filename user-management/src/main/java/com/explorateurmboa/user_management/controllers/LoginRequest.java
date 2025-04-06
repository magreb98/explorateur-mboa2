package com.explorateurmboa.user_management.controllers;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
   
    @NotBlank
    private String password;

    @NotBlank
    private String email;

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
