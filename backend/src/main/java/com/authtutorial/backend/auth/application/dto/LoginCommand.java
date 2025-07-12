package com.authtutorial.backend.auth.application.dto;

import lombok.Getter;

@Getter
public class LoginCommand {
    private String name;
    private String password;
}
