package com.authtutorial.backend.user.application;

import com.authtutorial.backend.user.application.dto.RegisterCommand;

public interface UserService {
    void register(RegisterCommand registerCommand);
}
