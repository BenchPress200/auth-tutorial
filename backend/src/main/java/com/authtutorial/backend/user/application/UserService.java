package com.authtutorial.backend.user.application;

import com.authtutorial.backend.user.application.dto.RegisterCommand;
import com.authtutorial.backend.user.application.dto.UserDetailsResponse;

public interface UserService {
    void register(RegisterCommand registerCommand);

    UserDetailsResponse getUserDetails(long userId);
}
