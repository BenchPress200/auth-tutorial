package com.authtutorial.backend.auth.application;

import com.authtutorial.backend.auth.application.dto.UserIdQuery;

public interface AuthService {
    UserIdQuery whoAmI();
}
