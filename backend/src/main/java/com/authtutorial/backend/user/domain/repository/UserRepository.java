package com.authtutorial.backend.user.domain.repository;

import com.authtutorial.backend.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);
}
