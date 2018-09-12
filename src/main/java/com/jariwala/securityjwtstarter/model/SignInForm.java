package com.jariwala.securityjwtstarter.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignInForm {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}