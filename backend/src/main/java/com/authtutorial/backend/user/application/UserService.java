package com.authtutorial.backend.user.application;

import com.authtutorial.backend.user.application.dto.RegisterCommand;
import com.authtutorial.backend.user.application.dto.UserDetailsQuery;

public interface UserService {
    void register(RegisterCommand registerCommand);

    UserDetailsQuery getUserDetails(long userId);
}
